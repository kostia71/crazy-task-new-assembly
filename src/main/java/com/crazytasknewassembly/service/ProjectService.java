package com.crazytasknewassembly.service;

import com.crazytasknewassembly.api.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> findAll();
    ProjectDto findByIdDto(Long id);
    ProjectDto save(ProjectDto projectDto);
    void delete(Long userId);
}
