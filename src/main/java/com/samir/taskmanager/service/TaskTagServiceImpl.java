package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.repository.TaskTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTagServiceImpl implements TaskTagService {
    @Autowired
    private TaskTagRepository taskTagRepository;

    @Override
    public List<TaskTag> getAllTaskTags(String sortBy) {
        return taskTagRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    }

}
