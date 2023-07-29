package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.dto.SubLowTag;
import com.samir.taskmanager.dto.SubTaskTag;
import com.samir.taskmanager.entity.LowLevelSubTask;
import com.samir.taskmanager.repository.LowLevelSubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubServiceImpl implements SubService {
    @Autowired
    private LowLevelSubTaskRepository lowLevelSubTaskRepository;

    @Override
    public List<SubLowTag> getJoinSubLowTag(Long columnName) {
        return lowLevelSubTaskRepository.getJoinSubLowTag(columnName);
    }

    @Override
    public List<SubTaskTag> getJoinSubTaskTagByALlt(Long id, Long columnName) {
        return lowLevelSubTaskRepository.getJoinSubTaskTagByALltId(id, columnName);
    }

    @Override
    public LowLevelSubTask saveSubTask(LowLevelSubTask lowLevelSubTask) {
        lowLevelSubTask.setLowLevelSubTaskName(NullChecker.check(lowLevelSubTask.getLowLevelSubTaskName()));
        return lowLevelSubTaskRepository.save(lowLevelSubTask);
    }

    @Override
    public LowLevelSubTask updateSubTask(LowLevelSubTask lowLevelSubTask) {
        lowLevelSubTask.setLowLevelSubTaskName(NullChecker.check(lowLevelSubTask.getLowLevelSubTaskName()));
        return lowLevelSubTaskRepository.save(lowLevelSubTask);
    }

    @Override
    public LowLevelSubTask getSubTaskById(Long id) {
        return lowLevelSubTaskRepository.findById(id).get();
    }

    @Override
    public void deleteSubTaskById(Long id) {
        lowLevelSubTaskRepository.deleteById(id);
    }

    @Override
    public void deleteAllLowLevelSubTasks() {
        lowLevelSubTaskRepository.deleteAll();
    }
}
