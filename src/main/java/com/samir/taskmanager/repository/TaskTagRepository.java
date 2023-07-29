package com.samir.taskmanager.repository;

import com.samir.taskmanager.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
}
