package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HltTaskTag {
    private Long highLevelTaskID;
    private String highLevelTaskName;
    private Long taskTagID;
    private String taskTagName;
}
