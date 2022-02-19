package com.crazytasknewassembly.utils;

import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.api.dto.TaskStateDto;
import com.crazytasknewassembly.service.ProjectServiceI;
import com.crazytasknewassembly.service.TaskStateService;
import com.crazytasknewassembly.service.TaskStateServiceI;
import com.crazytasknewassembly.store.entities.TaskStateEntity;
import com.crazytasknewassembly.store.repositories.TaskStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {

    private final ProjectServiceI projectService;
    private final TaskStateServiceI taskStateService;

    @Override
    public void run(String... args) {

        ProjectDto projectdto1 = new ProjectDto();
        projectdto1.setName("Project_1");
        ProjectDto projectdto2 = new ProjectDto();
        projectdto2.setName("Project_2");

        projectService.save(projectdto1);
        projectService.save(projectdto2);

        for (ProjectDto p : projectService.findAll()) {
            System.out.println(p);
        }
    }
}
