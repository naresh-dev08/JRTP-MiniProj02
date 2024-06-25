package com.nt.model;

;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    private Integer courseID;
    private String courseName;
    private String location;
    private String courseCategory;
    private String facultyName;
    private Double fee;
    private String trainingMode;
    private String courseStatus;
    private LocalDateTime startOn;
    private Long adminContact;


}
