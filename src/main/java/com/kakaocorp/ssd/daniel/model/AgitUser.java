package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AgitUser {
    private String id;
    @JsonProperty("agit_id")
    private String agitId;
}
