package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WallMessage {
    @JsonProperty("content_data")
    private ContentData contentData;
}
