package com.crazytasknewassembly.api.controllers;

import com.crazytasknewassembly.api.controllers.helpers.ControllerHelper;
import com.crazytasknewassembly.api.dto.AckDto;
import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.api.exceptions.BadRequestException;
import com.crazytasknewassembly.api.converters.ProjectDtoConverter;
import com.crazytasknewassembly.api.exceptions.NotFoundException;
import com.crazytasknewassembly.service.ProjectServiceImpl;
import com.crazytasknewassembly.store.entities.ProjectEntity;
import com.crazytasknewassembly.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectServiceImpl projectService;
    ProjectRepository projectRepository;
    ProjectDtoConverter projectDtoFactory;
    ControllerHelper controllerHelper;

    public static final String GET_PROJECTS = "/api/projects/get";
    public static final String CREATE_PROJECT = "/api/projects/create";
//    public static final String EDIT_PROJECT = "/api/projects/edit{id}";
    public static final String UPDATE_PROJECT = "/api/projects/update{id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    public static final String GET_PROJECTS_BODY = "/api/body/projects";
    public static final String GET_PROJECT_BODY = "/api/body/projects/{projectId}";
    public static final String CREATE_PROJECT_BODY = "/api/body/projects";
    public static final String UPDATE_PROJECT_BODY = "/api/body/projects/{project_id}";
    public static final String DELETE_PROJECT_BODY = "/api/body/projects/{project_id}";

    // получить проекты - изменение в строке /project_id
    @GetMapping(GET_PROJECTS)
    public List<ProjectDto> getProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    // получить все проекты body
    @GetMapping(value = GET_PROJECTS_BODY)
    public ResponseEntity<?> getProject() {
        List<ProjectDto> productDtos = projectService.findAll();
        return projectService != null && !productDtos.isEmpty()
                ? ResponseEntity.ok(productDtos)
                : ResponseEntity.ok().body(HttpStatus.NOT_FOUND);
    }

    // получить по id body
    @GetMapping(GET_PROJECT_BODY)
    public ResponseEntity<?> getProjects(@PathVariable Long projectId) {
        ProjectDto projectDto = projectService.findByIdDto(projectId);
        return projectDto != null
                ? ResponseEntity.ok(projectDto)
                : ResponseEntity.ok().body(HttpStatus.NOT_FOUND);
    }

    // создать новый проект - изменение в строке запроса /project_id
    @PutMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        if (name.trim().isEmpty()) {
            throw new BadRequestException("Name can't be empty");
        }

        projectService
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        ProjectEntity projectEntity = projectService.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );

        // TODO: insert entity method
        return projectDtoFactory.makeProjectDto(projectEntity);
    }

    //  создать новый проект body
    @PostMapping(value = CREATE_PROJECT_BODY)
    public ResponseEntity<?> createNewProduct(@RequestBody ProjectDto projectDto) {
        projectService.save(projectDto);
        return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

//    // изменить проект - изменение в строке запроса /project_id
//    @PatchMapping(EDIT_PROJECT)
//    public ProjectDto editPatch(@PathVariable("id") Long projectId,
//                                @RequestParam String name) {
//        if (name.trim().isEmpty()) {
//            throw new BadRequestException("Name can't be empty");
//        }
//
//        ProjectEntity projectEntity = projectService
//                .findById(projectId)
//                .orElseThrow(() ->
//                        new NotFoundException(
//                                String.format(
//                                        "Project with \"%s\" doesn't exists.",
//                                        projectId)
//                        )
//                );
//
//        projectService
//                .findByName(name)
//                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
//                .ifPresent(anotherProject -> {
//                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
//                });
//
//        projectEntity.setName(name);
//
//        projectEntity = projectService.saveAndFlush(projectEntity);
//
//        // TODO: insert entity method
//        return projectDtoFactory.makeProjectDto(projectEntity);
//    }

    //  изменить строку - изменения в адресной строке
    @PutMapping(UPDATE_PROJECT)
    public ResponseEntity<?> change(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "name") String name){
    ProjectDto projectDto = projectService.findByIdDto(id);
        projectDto.setName(name);
        projectService.save(projectDto);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }


    // изменить проект body - изменение в теле запроса
    @PutMapping(value = UPDATE_PROJECT_BODY)
    public ResponseEntity<?> createUpdateProduct(@RequestBody ProjectDto projectDto) {
        projectService.save(projectDto);
        return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    // удаление проекта по id - изменение в строке запроса /project_id
    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectService.delete(projectId);

        return AckDto.makeDefault(true);
    }

    // удаление проекта по id body - изменение в теле запроса /project_id
    @DeleteMapping(DELETE_PROJECT_BODY)
    public ResponseEntity<?> delete(@PathVariable("project_id") Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectService.delete(projectId);

        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
