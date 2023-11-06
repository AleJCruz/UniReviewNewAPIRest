package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.Teacher;

import java.util.List;

public interface TeacherService {
    public Teacher register(Teacher teacher);
    public Teacher updateTeacher(Teacher teacher) throws Exception;
    public Teacher deleteTeacher(Long id) throws Exception;
    public List<Teacher> listTeacher();
    public Teacher addCourse(Long teacherID, Long courseID) throws Exception;
    public List<Teacher> listByFullName(String fullname);
    public List<Teacher> listTeachersByFilters(double qualificationFrom, double qualificationTo, Long rigurosityId, Long courseId, String fullNameTeacher);
    public List<Teacher> listTeachersByRigurosity(String rigurosityName);
    public List<Teacher> listTeachersByCourse(Long courseId);
    public List<Teacher> listTeachersByQualification(double qualificationFrom, double qualificationTo);
    public Teacher getTeacherByID(Long ID);
}
