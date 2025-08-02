package com.midlane.project_management_tool_project_service.mapper;

import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SprintMapper {

    SprintMapper INSTANCE = Mappers.getMapper(SprintMapper.class);

    SprintDTO toDTO(Sprint sprint);

    Sprint toEntity(SprintDTO dto);
}
