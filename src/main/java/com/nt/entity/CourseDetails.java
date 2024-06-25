package com.nt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "JRTP_COURSE_DEATILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetails {

    @Id
    @GeneratedValue
    private Integer courseID;
    @Column(length = 50)
    private String courseName;
    @Column(length = 50)
    private String location;
    @Column(length = 50)
    private String courseCategory;
    @Column(length = 50)
    private String facultyName;

    private Double fee;
    @Column(length = 20)
    private String trainingMode;
    @Column(length = 20)
    private String courseStatus;

    private LocalDateTime startOn;
    @Column(length = 25)
    private String adminName;

    private Long adminContact;


    @CreationTimestamp
    @Column(insertable = true, updatable = false)
    private LocalDateTime createdDate;
    @UpdateTimestamp
    @Column(insertable = false, updatable = true)
    private LocalDateTime updatedDate;

    @Column(length = 25)
    private String createdBy;
    @Column(length = 25)
    private String updatedBy;


}
