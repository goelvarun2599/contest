package com.example.contest.controller;

import com.example.contest.ContestRepository;
import com.example.contest.entity.Contest;
import com.example.contest.entity.User;
import com.example.contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contest")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestService contestService;

    @PostMapping
    public Contest save(@RequestBody Contest contest){

        return contestService.saveContest(contest);
    }

    @GetMapping(path = "/{id}")
    public Contest getContestById(@PathVariable String id){
        return contestService.getContestByID(id);
    }


    @GetMapping(path = "/{id}/ranking")
    public List<User> getRanks(@PathVariable String id){
        return contestService.getRanks(id);
    }


}
