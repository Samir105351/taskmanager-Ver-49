package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LltHltTaskTag {
    private Long lowLevelTaskID;
    private String lowLevelTaskName;
    private Long highLevelTaskID;
    private String highLevelTaskName;
    private Long taskTagID;
    private String taskTagName;
}
