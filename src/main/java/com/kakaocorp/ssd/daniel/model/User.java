package com.kakaocorp.ssd.daniel.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String userId;
    private String url;
    private List<WorkingTime> workingTimeList;
}
