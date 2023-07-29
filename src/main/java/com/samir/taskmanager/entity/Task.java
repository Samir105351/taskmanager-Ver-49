package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskID;
    @Column(unique = true, nullable = false)
    private String taskName;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Sister sister;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<HighLevelTask> highLevelTaskList;
    @OneToOne(cascade = CascadeType.ALL)
    private TaskTag taskTag;

}
