package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.dto.HltTaskTag;
import com.samir.taskmanager.dto.HltTaskTaskTag;
import com.samir.taskmanager.entity.HighLevelTask;
import com.samir.taskmanager.repository.HighLevelTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighLevelTaskServiceImpl implements HighLevelTaskService {
    @Autowired
    private HighLevelTaskRepository highLevelTaskRepository;

    @Override
    public List<HighLevelTask> getAllHighLevelTasks() {
        return highLevelTaskRepository.findAll(Sort.by(Sort.Direction.ASC, "highLevelTaskID"));
    }

    @Override
    public HighLevelTask saveHighLevelTask(HighLevelTask highLevelTask) {
        highLevelTask.setHighLevelTaskName(NullChecker.check(highLevelTask.getHighLevelTaskName()));
        return highLevelTaskRepository.save(highLevelTask);
    }

    @Override
    public HighLevelTask getHighLevelTaskById(Long id) {
        return highLevelTaskRepository.findById(id).get();
    }

    @Override
    public HighLevelTask updateHighLevelTask(HighLevelTask highLevelTask) {
        highLevelTask.setHighLevelTaskName(NullChecker.check(highLevelTask.getHighLevelTaskName()));
        return highLevelTaskRepository.save(highLevelTask);
    }

    @Override
    public void deleteHighTaskById(Long id) {
        highLevelTaskRepository.deleteById(id);
    }

    @Override
    public void deleteAllHighLevelTasks() {
        highLevelTaskRepository.deleteAll();
    }

    @Override
    public List<HltTaskTaskTag> getAllHltTaskTaskTag(Long columnNumber) {
        return highLevelTaskRepository.getJoinHltTaskTaskTag(columnNumber);
    }

    @Override
    public List<HltTaskTag> getAllHltTaskTagUnderATaskId(Long id, Long columnNumber) {
        return highLevelTaskRepository.getJoinHltTaskTagByTaskId(id, columnNumber);
    }
}
