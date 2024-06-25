package com.nt.repo;

import com.nt.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ICourseDetailsRepo extends JpaRepository<CourseDetails, Integer> {

    @Query("select distinct(courseCategory) from CourseDetails")
    Set<String> getUniqueCourseCategories();

    @Query("select distinct(facultyName) from CourseDetails")
    Set<String> getUniqueFacultyNames();

    @Query("select distinct(trainingMode) from CourseDetails")
    Set<String> getUniqueTrainingModes();
}
