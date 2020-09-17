package com.kakaocorp.ssd.daniel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignee {
    private String id;
    @JsonProperty("agit_id")
    private String agitId;
}
