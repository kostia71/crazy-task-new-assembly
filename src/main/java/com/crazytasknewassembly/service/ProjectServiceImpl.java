package com.crazytasknewassembly.service;

import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.api.factories.ProjectDtoFactory;
import com.crazytasknewassembly.store.entities.ProjectEntity;
import com.crazytasknewassembly.store.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectDtoFactory projectDtoFactory;

    // поиск всех Dto
    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    // поиск по id Dto
    @Override
    public ProjectDto findById(Long id) {
        return projectDtoFactory.makeProjectDto(projectRepository.findById(id).orElse(new ProjectEntity()));
    }

//    // запись Dto
//    @Override
//    public ProjectDto save(ProjectDto projectDto) {
//        ProjectEntity projectEntity = projectRepository.save(projectDtoFactory.makeProjectDto(projectDto));
//        return projectDtoFactory.makeProjectDto(projectEntity);
//    }

    // удаление Entity
    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

//    // запись Entity
//    public void saveAll(List<ProjectEntity> entities){
//        projectRepository.saveAll(entities);
//    }
//
//    // запись Entity
//    public void save(ProjectEntity projectEntity){
//        projectRepository.save(projectEntity);
//    }
//
//    // поиск всех Entity
//    public List<ProjectEntity> getAll(){
//        return projectRepository.findAll();
//    }
}