package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubTaskTag {
    private Long lowLevelSubTaskID;
    private String lowLevelSubTaskName;
    private Long taskTagID;
    private String taskTagName;
}
