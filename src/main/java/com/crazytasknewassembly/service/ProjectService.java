package com.crazytasknewassembly.service;

import com.crazytasknewassembly.api.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> findAll();
    ProjectDto findById(Integer id);
    ProjectDto save(ProjectDto projectDto);
    void delete(Integer userId);
}
