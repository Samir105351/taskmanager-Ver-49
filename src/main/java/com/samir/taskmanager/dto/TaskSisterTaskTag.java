package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskSisterTaskTag {
    private Long taskID;
    private String taskName;
    private Long sisterId;
    private String sisterName;
    private Long taskTagID;
    private String taskTagName;
}
