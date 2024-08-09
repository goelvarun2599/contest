package com.example.contest.service.impl;

import com.example.contest.*;
import com.example.contest.client.CodeforcesClient;
import com.example.contest.client.domain.UserSubmission;
import com.example.contest.entity.Contest;
import com.example.contest.entity.Question;
import com.example.contest.entity.User;
import com.example.contest.service.ContestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    private final CodeforcesClient codeforcesClient;

    @Override
    public Contest getContestByID(String id) {
        return contestRepository.findById(id).orElse(null);
    }

    @Override
    public Contest saveContest(Contest contest){
       ///// Creates or updates existing contest
        if(StringUtils.hasLength(contest.getId())){
            Contest existingContest = contestRepository.findById(contest.getId()).orElseThrow(() -> new IllegalArgumentException("Not a valid contest id"));
            if(LocalDateTime.now().isAfter(existingContest.getStartTime())){
                /////  contest is already started
                throw new IllegalStateException("Contest can't be updated after start time");
            }
        }
        return contestRepository.save(contest);
    }

    @Override
    public List<User> getRanks(String id) {
        Contest contest = contestRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Not a valid contest id"));
        if(!Boolean.TRUE.equals(contest.getIsContestFinished())){
            fetchLatestRanks(contest);
        }
        return contest.getUsers();
    }

    private void fetchLatestRanks(Contest contest) {
        /// Multiple users did the questions, so time based needs to be there. Take score only if verdict is ok
        Set<String> questionList = contest.getQuestions().stream().map(Question::getName).collect(Collectors.toSet());
        for (User user: contest.getUsers()) {
            //// CF api fetches results from users, not user and problem intersection
            String userHandle = user.getHandle();
            UserSubmission userSubmissions = codeforcesClient.fetchUserSubmissions(userHandle, 1l, 1000l);
            updateUserScore(user, questionList, userSubmissions, contest.getStartTime(), contest.getTimeDurationInMins());
        }

        contest.getUsers().sort((user1, user2) -> {
            if (user1.getScore() > user2.getScore()) return -1;
            else if (user1.getScore() < user2.getScore()) return 1;
            return user1.getLastSubmissionTimeInSeconds().compareTo(user2.getLastSubmissionTimeInSeconds());
        });
        AtomicReference<Integer> currentRank = new AtomicReference<>(1);
        contest.getUsers().forEach(user -> user.setRank(currentRank.getAndSet(currentRank.get() + 1)));
        contestRepository.save(contest);
    }

    private void updateUserScore(User user, Set<String> questionList, UserSubmission userSubmissions, LocalDateTime startTime, Long timeDurationInMins) {
        Long score = 0l;
        Long lastSubmissionTimeInSeconds = 0l;
        for (UserSubmission.Submission submission : userSubmissions.getResult()) {
            /////   Checking if submission is valid or not
            String questionName = submission.getProblem().getName();
            String index = submission.getProblem().getIndex();
            Long creationTimeInSeconds = submission.getCreationTimeSeconds();
            Long contestStartTimeSeconds = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
            Long contestEndTimeSeconds = contestStartTimeSeconds + timeDurationInMins * 60;

            if(questionList.contains(questionName) && contestStartTimeSeconds <= creationTimeInSeconds &&
                    creationTimeInSeconds < contestEndTimeSeconds && "OK".equals(submission.getVerdict())){
                score += (index.charAt(0) - 'A');
                lastSubmissionTimeInSeconds = creationTimeInSeconds;
            }
            if(creationTimeInSeconds < contestStartTimeSeconds) {
                /////  Because if latestSubmission is before the contest is started, there will be no submissions in the contest duration
                break;
            }
        }
        user.setScore(score);
        user.setLastSubmissionTimeInSeconds(lastSubmissionTimeInSeconds);
    }
}
