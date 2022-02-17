package com.crazytasknewassembly.api.converters;

import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoConverter {

    public ProjectDto makeProjectDto(ProjectEntity entity) {

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProjectEntity makeProjectEntity(ProjectDto dto){
        return ProjectEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
