package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.TeacherReview;

import java.util.List;

public interface TeacherReviewService {
    public TeacherReview register(TeacherReview teacherReview);
    public TeacherReview updateTeacherReview(TeacherReview teacherReview) throws Exception;
    public TeacherReview deleteTeacherReview(Long id) throws Exception;
    public List<TeacherReview> listTeachersReviews();
    public List<TeacherReview> listTeachersByTeacherName(String prefix);
    public List<TeacherReview>listReviewsByTeacherId(Long id);
}
