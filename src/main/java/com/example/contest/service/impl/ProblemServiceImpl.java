package com.example.contest.service.impl;

import com.example.contest.client.CodeforcesClient;
import com.example.contest.client.domain.CodeforcesQuestions;
import com.example.contest.entity.Question;
import com.example.contest.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.DataMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final CodeforcesClient codeforcesClient;



    @Override
    public List<Question> fetchProblems(List<String> tags) {
        CodeforcesQuestions codeforcesQuestions = codeforcesClient.fetchQuestions(tags);
        if(codeforcesQuestions.getResult() != null){
            return codeforcesQuestions.getResult().getProblems();
        }
        return new LinkedList<>();
    }
}
