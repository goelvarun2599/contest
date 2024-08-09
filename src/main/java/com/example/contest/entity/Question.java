package com.example.contest.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Question {

    @NotNull
    private String name;

    @NotNull
    private String index;
}
