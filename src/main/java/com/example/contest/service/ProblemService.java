package com.example.contest.service;

import com.example.contest.client.domain.CodeforcesQuestions;
import com.example.contest.entity.Question;

import java.util.List;

public interface ProblemService {
    List<Question> fetchProblems(List<String> tags);
}
