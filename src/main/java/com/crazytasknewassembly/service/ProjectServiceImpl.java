package com.crazytasknewassembly.service;

import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.api.converters.ProjectDtoConverter;
import com.crazytasknewassembly.store.entities.ProjectEntity;
import com.crazytasknewassembly.store.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectDtoConverter projectDtoFactory;

    // поиск всех Dto
    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    // поиск по id Dto
    @Override
    public ProjectDto findByIdDto(Long projectId) {
        return projectDtoFactory.makeProjectDto(projectRepository.findById(projectId).orElse(new ProjectEntity()));
    }

    // запись Dto
    @Override
    public ProjectDto save(ProjectDto projectDto) {
        ProjectEntity projectEntity = projectRepository.save(projectDtoFactory.makeProjectEntity(projectDto));
        return projectDtoFactory.makeProjectDto(projectEntity);
    }

    // удаление Entity
    @Override
    public void delete(Long id) {
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

    // поиск по имени Entity
    public Optional<ProjectEntity> findByName(String name){
        return projectRepository.findByName(name);
    }

    // запись с возвратом id
    public ProjectEntity saveAndFlush(ProjectEntity projectEntity){
        return projectRepository.saveAndFlush(projectEntity);
    }

    // поиск по id
    public Optional<ProjectEntity> findById(Long projectId){
        return projectRepository.findById(projectId);
    }


}