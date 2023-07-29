package com.samir.taskmanager.repository;

import com.samir.taskmanager.entity.Sister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SisterRepository extends JpaRepository<Sister, Long> {
}
