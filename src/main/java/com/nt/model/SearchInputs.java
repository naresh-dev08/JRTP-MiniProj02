package com.nt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInputs {

    private String courseCategory;
    private String trainingMode;
    private String facultyName;
    private LocalDateTime startsOn;
}
