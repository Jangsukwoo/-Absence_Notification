package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentOfRequestModel {
    @JsonProperty("parent_id")
    private String parentId;
    private String text;
    private String reporter;
}
