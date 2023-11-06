package com.upc.unireview.repository;

import com.upc.unireview.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findCourseById(Long id);
}
