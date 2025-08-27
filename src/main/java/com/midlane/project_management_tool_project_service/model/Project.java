//package com.midlane.project_management_tool_project_service.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.util.List;
////
//@Entity
//@Table(name = "projects")
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Project {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String type;
//    private String templateType;
//    private Long org_id;
//
//
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "project_features", joinColumns = @JoinColumn(name = "project_id"))
//    @Column(name = "feature")
//    private List<String> features;
//}
package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String templateType;

    @Column(name = "org_id") // DB column is org_id, but Java field is orgId
    private Long orgId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_features", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "feature")
    private List<String> features;

    // Audit columns
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
