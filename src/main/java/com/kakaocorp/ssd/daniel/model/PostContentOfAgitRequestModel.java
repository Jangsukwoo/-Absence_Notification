package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostContentOfAgitRequestModel {
    @JsonProperty("wall_message")
    private WallMessage wallMessage;

    public List<Task> getTaskList() {
        return this.getWallMessage().getContentData().getTaskList();
    }
}
