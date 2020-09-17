package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Task {
    @JsonProperty("assignees")
    private List<Assignee> assigneeList;
}
