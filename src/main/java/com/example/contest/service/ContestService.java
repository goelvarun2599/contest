package com.example.contest.service;

import com.example.contest.entity.Contest;
import com.example.contest.entity.User;

import java.util.List;

public interface ContestService {
    Contest getContestByID(String id);

    Contest saveContest(Contest contest);


    List<User> getRanks(String id);
}
