package com.crazytasknewassembly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;
    private final ProjectDtoFactory projectDtoFactory;

    // поиск всех Dto
    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(projectDtoFactory::makeToProjectDto)
                .collect(Collectors.toList());
    }

    // поиск по id Dto
    @Override
    public ProjectDto findById(Integer id) {
        return projectDtoFactory.makeToProjectDto(projectRepository.findById(id).orElse(new ProjectEntity()));
    }

    // запись Dto
    @Override
    public ProjectDto save(ProjectDto projectDto) {
        ProjectEntity projectEntity = projectRepository.save(projectDtoFactory.makeToProjectEntity(projectDto));
        return projectDtoFactory.makeToProjectDto(projectEntity);
    }

    // удаление Entity
    @Override
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }

    // запись Entity
    public void saveAll(List<ProjectEntity> entities){
        projectRepository.saveAll(entities);
    }

    // запись Entity
    public void save(ProjectEntity projectEntity){
        projectRepository.save(projectEntity);
    }

    // поиск всех Entity
    public List<ProjectEntity> getAll(){
        return projectRepository.findAll();
    }
}