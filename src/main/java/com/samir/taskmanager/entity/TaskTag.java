package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskTagID;
    @Column(nullable = false)
    private String taskTagName;
    @OneToOne(mappedBy = "taskTag", cascade = CascadeType.ALL)
    private Task task;
    @OneToOne(mappedBy = "taskTag", cascade = CascadeType.ALL)
    private HighLevelTask highLevelTask;
    @OneToOne(mappedBy = "taskTag", cascade = CascadeType.ALL)
    private LowLevelTask lowLevelTask;
    @OneToOne(mappedBy = "taskTag", cascade = CascadeType.ALL)
    private LowLevelSubTask lowLevelSubTask;
}
