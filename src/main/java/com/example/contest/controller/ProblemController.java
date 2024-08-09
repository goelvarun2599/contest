package com.example.contest.controller;

import com.example.contest.entity.Question;
import com.example.contest.service.ProblemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/problems")
@AllArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    List<Question> fetchProblems(@RequestParam List<String> tags){
        return problemService.fetchProblems(tags);
    }

}
