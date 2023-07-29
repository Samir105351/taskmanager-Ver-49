package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "high_level_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HighLevelTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long highLevelTaskID;
    @Column(unique = true, nullable = false)
    private String highLevelTaskName;
    @ManyToOne
    private Task task;
    @OneToMany(mappedBy = "highLevelTask", cascade = CascadeType.ALL)
    private List<LowLevelTask> listLowLevelTask;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TaskTag taskTag;
}
