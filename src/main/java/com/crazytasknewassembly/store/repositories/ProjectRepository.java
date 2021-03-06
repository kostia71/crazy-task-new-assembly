package com.crazytasknewassembly.store.repositories;

import com.crazytasknewassembly.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByName(String name);

    Optional<ProjectEntity> findById(Long projectId);

    Stream<ProjectEntity> streamAllBy();

    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);

    ProjectEntity saveAndFlush(ProjectEntity projectEntity);
}
