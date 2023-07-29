package com.samir.taskmanager.repository;

import com.samir.taskmanager.dto.SubLowTag;
import com.samir.taskmanager.dto.SubTaskTag;
import com.samir.taskmanager.entity.LowLevelSubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LowLevelSubTaskRepository extends JpaRepository<LowLevelSubTask, Long> {
    @Query("select new com.samir.taskmanager.dto.SubLowTag(s.lowLevelSubTaskID,s.lowLevelSubTaskName,l.lowLevelTaskID,l.lowLevelTaskName,tg.taskTagID,tg.taskTagName) from LowLevelSubTask s,LowLevelTask l,TaskTag tg where s.lowLevelTask.lowLevelTaskID=l.lowLevelTaskID and s.taskTag.taskTagID=tg.taskTagID order by :column")
    public List<SubLowTag> getJoinSubLowTag(@Param("column") Long column);

    @Query("select new com.samir.taskmanager.dto.SubTaskTag(s.lowLevelSubTaskID,s.lowLevelSubTaskName,tg.taskTagID,tg.taskTagName) from LowLevelSubTask s, TaskTag tg where s.taskTag.taskTagID=tg.taskTagID and s.lowLevelTask.lowLevelTaskID = :id order by :column")
    public List<SubTaskTag> getJoinSubTaskTagByALltId(@Param("id") Long id, @Param("column") Long column);

}
