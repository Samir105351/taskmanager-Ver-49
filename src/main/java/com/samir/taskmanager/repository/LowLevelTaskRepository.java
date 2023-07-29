package com.samir.taskmanager.repository;

import com.samir.taskmanager.dto.LltHltTaskTag;
import com.samir.taskmanager.dto.LltTaskTag;
import com.samir.taskmanager.entity.LowLevelTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LowLevelTaskRepository extends JpaRepository<LowLevelTask, Long> {
    @Query("select new com.samir.taskmanager.dto.LltHltTaskTag(l.lowLevelTaskID,l.lowLevelTaskName,h.highLevelTaskID,h.highLevelTaskName,tg.taskTagID,tg.taskTagName) " +
            "from LowLevelTask l,HighLevelTask h,TaskTag tg where l.highLevelTask.highLevelTaskID=h.highLevelTaskID and l.taskTag.taskTagID=tg.taskTagID order by :column")
    public List<LltHltTaskTag> getJoinLltHltTaskTag(@Param("column") Long column);

    @Query("select new com.samir.taskmanager.dto.LltTaskTag(l.lowLevelTaskID,l.lowLevelTaskName,tg.taskTagID,tg.taskTagName) " +
            "from LowLevelTask l, TaskTag tg where l.taskTag.taskTagID=tg.taskTagID and l.highLevelTask.highLevelTaskID = :id order by :column")
    public List<LltTaskTag> getJoinLltTaskTagByHltId(@Param("id") Long id, @Param("column") Long column);

}
