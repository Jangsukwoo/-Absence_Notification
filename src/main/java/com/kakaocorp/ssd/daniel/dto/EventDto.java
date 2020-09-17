package com.kakaocorp.ssd.daniel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventDto {
    private String type;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("group_id")
    private String groupId;
    private String id;
    @JsonProperty("root_post_id")
    private String rootPostId;
    @JsonProperty("is_root")
    private boolean isRoot;
    @JsonProperty("is_reply")
    private boolean isReply;
    private String text;
    @JsonProperty("created_time")
    private String createdTime;
}
