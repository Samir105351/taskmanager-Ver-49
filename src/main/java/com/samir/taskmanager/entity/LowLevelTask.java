package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "low_level_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LowLevelTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lowLevelTaskID;
    @Column(unique = true, nullable = false)
    private String lowLevelTaskName;
    @ManyToOne
    private HighLevelTask highLevelTask;
    @OneToMany(mappedBy = "lowLevelTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LowLevelSubTask> lowLevelSubTaskList;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TaskTag taskTag;
}
