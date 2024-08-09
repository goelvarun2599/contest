package com.example.contest.client.domain;

import com.example.contest.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class CodeforcesQuestions {

    private String status;

    private Result result;


    @Data
    public class Result {

        List<Question> problems;

    }
}
