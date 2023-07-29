package com.samir.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sister_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sisterId;
    @Column(unique = true, nullable = false)
    private String sisterName;
    @OneToMany(mappedBy = "sister", cascade = CascadeType.ALL)
    private List<Task> taskList;
}
