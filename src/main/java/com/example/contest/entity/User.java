package com.example.contest.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

    @NotNull
    private String name;

    @NotNull
    private String handle;

    private Long score;

    private Long lastSubmissionTimeInSeconds;

    private Integer rank;
}
