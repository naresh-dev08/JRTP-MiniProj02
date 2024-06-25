package com.nt.repo;

import com.nt.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseDetailsRepo extends JpaRepository<Integer, CourseDetails> {

}
