package com.kakaocorp.ssd.daniel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventRequestDto {
    @JsonProperty("workspace_id")
    private int workspaceId;
    @JsonProperty("application_id")
    private int applicationId;
    private EventDto event;
    @JsonProperty("event_time")
    private String eventTime;
    private String challenge;
    private String type;

    public String getRootPostId() {
        return this.getEvent().getRootPostId();
    }
}
