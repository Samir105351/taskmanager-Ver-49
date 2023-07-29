package com.samir.taskmanager.repository;

import com.samir.taskmanager.dto.TaskSisterTaskTag;
import com.samir.taskmanager.dto.TaskTaskTag;
import com.samir.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select new com.samir.taskmanager.dto.TaskSisterTaskTag(t.taskID,t.taskName,s.sisterId,s.sisterName,tg.taskTagID,tg.taskTagName) from Task t,Sister s,TaskTag tg where t.sister.sisterId=s.sisterId and t.taskTag.taskTagID=tg.taskTagID order by :column")
    public List<TaskSisterTaskTag> getJoinTaskServiceTaskTag(@Param("column") Long column);

    @Query("select new com.samir.taskmanager.dto.TaskTaskTag(t.taskID, t.taskName, tg.taskTagID, tg.taskTagName) from Task t, TaskTag tg where t.taskTag.taskTagID = tg.taskTagID and t.sister.sisterId = :id order by :column")
    public List<TaskTaskTag> getJoinTaskTaskTagBySystemId(@Param("id") Long id, @Param("column") Long column);

}
