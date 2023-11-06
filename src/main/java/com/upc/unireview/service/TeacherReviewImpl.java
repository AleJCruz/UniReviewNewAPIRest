package com.upc.unireview.service;

import com.upc.unireview.entities.Course;
import com.upc.unireview.entities.Teacher;
import com.upc.unireview.entities.TeacherReview;
import com.upc.unireview.interfaceservice.TeacherReviewService;
import com.upc.unireview.repository.TeacherRepository;
import com.upc.unireview.repository.TeacherReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherReviewImpl implements TeacherReviewService {
    @Autowired
    private TeacherReviewRepository teacherReviewRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    public TeacherReview register(TeacherReview teacherReview){
        return teacherReviewRepository.save(teacherReview);
    }
    public TeacherReview updateTeacherReview(TeacherReview teacherReview) throws Exception{
        //controlar que no se repita el email al actualizar
        teacherReviewRepository.findById(teacherReview.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return teacherReviewRepository.save(teacherReview);
    }

    public TeacherReview deleteTeacherReview(Long id) throws Exception{
        TeacherReview teacherReview = teacherReviewRepository.findById(id).orElseThrow(()->new Exception("No se encontro la review"));
        teacherReviewRepository.delete(teacherReview);
        return teacherReview;
    }
    public List<TeacherReview> listTeachersReviews(){
        return teacherReviewRepository.findAll();
    }

    public List<TeacherReview> listTeachersByTeacherName(String prefix){
        return teacherReviewRepository.getTeacherReviewsByTeacher_FullnameStartingWith(prefix);
    }
    public List<TeacherReview>listReviewsByTeacherId(Long id){
        return teacherReviewRepository.findTeacherReviewsByTeacher_Id(id);
    }
    //siempre se tiene que calcular rigurosidad de un docente al momento de ingresar una reseña a su nombre

}
