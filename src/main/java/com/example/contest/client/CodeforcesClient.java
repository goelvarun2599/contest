package com.example.contest.client;

import com.example.contest.client.domain.CodeforcesQuestions;
import com.example.contest.client.domain.UserSubmission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "codeforces", url = "${feign.client.url.codeforces}")
public interface CodeforcesClient {

////  check users submission and calculate rank
    //// get all the questions

    @GetMapping(value = "/api/problemset.problems")
    CodeforcesQuestions fetchQuestions(@RequestParam List<String> tags);

    @GetMapping(value = "/api/user.status")
    UserSubmission fetchUserSubmissions(@RequestParam(value = "handle", required = true) String handle, @RequestParam Long from, @RequestParam Long to);

}
