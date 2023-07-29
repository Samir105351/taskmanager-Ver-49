package com.samir.taskmanager.service;

import com.samir.taskmanager.entity.Sister;

import java.util.List;

public interface SisterService {
    List<Sister> getAllSister(String sortBy);

    Sister saveSister(Sister sister);

    Sister getSisterById(Long id);

    Sister updateSister(Sister sister);

    void deleteSisterById(Long id);

    void deleteAllSisters();
}
