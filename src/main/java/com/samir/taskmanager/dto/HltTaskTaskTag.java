package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HltTaskTaskTag {
    private Long highLevelTaskID;
    private String highLevelTaskName;
    private Long taskID;
    private String taskName;
    private Long taskTagID;
    private String taskTagName;
}
