package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ContentData {
    @JsonProperty("task")
    private List<Task> taskList;
}
