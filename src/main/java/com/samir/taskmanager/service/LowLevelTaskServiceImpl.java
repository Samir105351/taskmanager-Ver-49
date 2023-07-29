package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.dto.LltHltTaskTag;
import com.samir.taskmanager.dto.LltTaskTag;
import com.samir.taskmanager.entity.LowLevelTask;
import com.samir.taskmanager.repository.LowLevelTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LowLevelTaskServiceImpl implements LowLevelTaskService {
    @Autowired
    private LowLevelTaskRepository lowLevelTaskRepository;

    @Override
    public List<LltHltTaskTag> getAllLltHltTaskTag(Long columnNumber) {

        return lowLevelTaskRepository.getJoinLltHltTaskTag(columnNumber);
    }

    @Override
    public List<LltTaskTag> getAllLltTaskTagUnderAHlt(Long id, Long columnNumber) {
        return lowLevelTaskRepository.getJoinLltTaskTagByHltId(id, columnNumber);
    }

    @Override
    public LowLevelTask saveLowLevelTask(LowLevelTask lowLevelTask) {
        lowLevelTask.setLowLevelTaskName(NullChecker.check(lowLevelTask.getLowLevelTaskName()));
        return lowLevelTaskRepository.save(lowLevelTask);
    }

    @Override
    public LowLevelTask updateLowLevelTask(LowLevelTask lowLevelTask) {
        lowLevelTask.setLowLevelTaskName(NullChecker.check(lowLevelTask.getLowLevelTaskName()));
        return lowLevelTaskRepository.save(lowLevelTask);
    }

    @Override
    public LowLevelTask getLowLevelTaskById(Long id) {
        return lowLevelTaskRepository.findById(id).get();
    }

    @Override
    public List<LowLevelTask> getAllLowLevelTasks() {
        return lowLevelTaskRepository.findAll();
    }

    @Override
    public void deleteLowLevelTaskByID(Long id) {
        lowLevelTaskRepository.deleteById(id);
    }

    @Override
    public void deleteAllLowLevelTasks() {
        lowLevelTaskRepository.deleteAll();
    }
}
