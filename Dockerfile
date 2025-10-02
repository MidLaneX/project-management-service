# Multi-stage build for smaller image size
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for dependency caching)
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies only (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:resolve -B

# Copy source code (separate layer for faster rebuilds when only code changes)
COPY src ./src

# Build the application (dependencies will be downloaded during build)
RUN ./mvnw clean package -DskipTests -B

# Production stage
FROM eclipse-temurin:21-jre-alpine

# Install wget for health checks (using wget instead of curl for consistency with docker-compose)
RUN apk add --no-cache wget

# Create non-root user for security
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

# Set working directory
WORKDIR /app

# Copy the JAR file from builder stage
COPY --from=builder /app/target/project_management_tool_project_service-0.0.1-SNAPSHOT.jar app.jar

# Change ownership to spring user
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

# Expose port
EXPOSE 8083

# Environment variables with defaults
ENV SERVER_PORT=8083
ENV DB_URL=jdbc:postgresql://localhost:5434/project_service_db
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=postgres
ENV JWT_SECRET=myVerySecureJwtSecretKeyThatIsAtLeast32CharactersLongForHS256Algorithm

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${SERVER_PORT}/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
