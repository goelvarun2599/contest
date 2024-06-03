package com.example.contest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/student")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @PostMapping
    public Contest save(@RequestBody Contest contest){
        return contestRepository.save(contest);
    }

}
