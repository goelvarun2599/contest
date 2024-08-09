package com.example.contest.client.domain;

import com.example.contest.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class UserSubmission {

    private String status;

    private List<Submission> result;

    @Data
    public static class Submission {

        private Long creationTimeSeconds;

        private String verdict;

        private Question problem;
    }
}
