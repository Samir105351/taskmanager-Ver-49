package com.samir.taskmanager.repository;

import com.samir.taskmanager.dto.HltTaskTag;
import com.samir.taskmanager.dto.HltTaskTaskTag;
import com.samir.taskmanager.entity.HighLevelTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HighLevelTaskRepository extends JpaRepository<HighLevelTask, Long> {
    @Query("select new com.samir.taskmanager.dto.HltTaskTaskTag(h.highLevelTaskID,h.highLevelTaskName,t.taskID,t.taskName,tg.taskTagID,tg.taskTagName) from HighLevelTask h,Task t,TaskTag tg where h.task.taskID=t.taskID and h.taskTag.taskTagID=tg.taskTagID order by :column")
    public List<HltTaskTaskTag> getJoinHltTaskTaskTag(@Param("column") Long column);

    @Query("select new com.samir.taskmanager.dto.HltTaskTag(h.highLevelTaskID,h.highLevelTaskName,tg.taskTagID,tg.taskTagName) from HighLevelTask h, TaskTag tg where h.taskTag.taskTagID=tg.taskTagID and h.task.taskID = :id order by :column")
    public List<HltTaskTag> getJoinHltTaskTagByTaskId(@Param("id") Long id, @Param("column") Long column);
}
