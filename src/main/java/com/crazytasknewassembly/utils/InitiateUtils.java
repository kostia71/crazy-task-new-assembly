package com.crazytasknewassembly.utils;

import com.crazytasknewassembly.api.dto.ProjectDto;
import com.crazytasknewassembly.service.ProjectServiceImpl;
import com.crazytasknewassembly.store.repositories.ProjectRepository;
import com.crazytasknewassembly.store.entities.ProjectEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {

    private final ProjectServiceImpl projectService;

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
