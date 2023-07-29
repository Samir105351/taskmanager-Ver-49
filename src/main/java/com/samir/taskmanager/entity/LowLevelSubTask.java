package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "low_level_sub_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LowLevelSubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lowLevelSubTaskID;
    @Column(unique = true, nullable = false)
    private String lowLevelSubTaskName;
    @ManyToOne
    private LowLevelTask lowLevelTask;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TaskTag taskTag;
}
