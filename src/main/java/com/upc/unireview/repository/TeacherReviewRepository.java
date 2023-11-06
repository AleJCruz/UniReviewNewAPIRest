package com.upc.unireview.repository;

import com.upc.unireview.entities.TeacherReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherReviewRepository extends JpaRepository<TeacherReview,Long> {
    public List<TeacherReview> getTeacherReviewsByTeacher_FullnameStartingWith(String Prefix);
    public List<TeacherReview> findTeacherReviewsByTeacher_Id(Long id);
}
