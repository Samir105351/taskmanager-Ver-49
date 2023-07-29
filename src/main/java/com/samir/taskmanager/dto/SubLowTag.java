package com.samir.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubLowTag {
    private Long lowLevelSubTaskID;
    private String lowLevelSubTaskName;
    private Long lowLevelTaskID;
    private String lowLevelTaskName;
    private Long TaskTagID;
    private String TaskTagName;
}
