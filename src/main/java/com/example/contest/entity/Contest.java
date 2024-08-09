package com.example.contest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "contest")
@Getter
@Setter
public class Contest {

    @Id
    private String id;

    @NotBlank
    private String contestName;

    @NotNull
    private Long timeDurationInMins;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private Boolean isContestFinished;

    private List<User> users;

    private List<Question> questions;

}
