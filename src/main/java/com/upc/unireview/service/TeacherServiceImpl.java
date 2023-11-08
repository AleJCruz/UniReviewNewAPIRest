package com.upc.unireview.service;

import com.upc.unireview.entities.*;
import com.upc.unireview.interfaceservice.TeacherService;
import com.upc.unireview.repository.CourseRepository;
import com.upc.unireview.repository.RigurosityRepository;
import com.upc.unireview.repository.TeacherRepository;
import com.upc.unireview.repository.TeacherReviewRepository;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository; //necesario para ingresar cursos en un profesor
    @Autowired
    private TeacherReviewRepository teacherReviewRepository;
    @Autowired
    private RigurosityRepository rigurosityRepository;
    public Teacher register(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Teacher teacher) throws Exception{
        //controlar que no se repita el email al actualizar
        teacherRepository.findById(teacher.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return teacherRepository.save(teacher);
    }

    public Teacher deleteTeacher(Long id) throws Exception{
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new Exception("No se encontro el profesor"));
        teacherRepository.delete(teacher);
        return teacher;
    }
    //Optimizar código
    public List<Teacher> listTeacher(){
        List<Teacher> l1 = teacherRepository.findAll();
        for (Teacher t:l1
        ) {
            CalcQualification(t);
            SetRigurosityTeacher(t);
            teacherRepository.save(t);
        }
        return l1;
    }

    public Teacher addCourse(Long teacherID, Long courseID) throws Exception{
        Teacher teacher = teacherRepository.findById(teacherID).orElseThrow(()->new Exception("No se encontro el profesor"));
        Course course = courseRepository.findById(courseID).orElseThrow(()->new Exception("No se encontro el curso"));
        teacher.getCoursearray().add(course);
        return teacherRepository.save(teacher);
    }
    //Optimizar código
    public List<Teacher> listByFullName(String fullname){
        List<Teacher> l1 = teacherRepository.findTeacherByFullnameStartingWith(fullname);
        for (Teacher t:l1
             ) {
            CalcQualification(t);
            SetRigurosityTeacher(t);
            teacherRepository.save(t);
        }
        return l1;
    }
    public void CalcQualification(Teacher teacher){
        List<TeacherReview> listReviews = teacherReviewRepository.findTeacherReviewsByTeacher_Id(teacher.getId());
        double sum = 0;
        for (TeacherReview teacherReview : listReviews) {
            sum +=((double) teacherReview.getScore());
        }
        teacher.setQualification(sum/ ((double) listReviews.size()));
    }

    //FILTRO AVANZADO DE BUSQUEDA
    public List<Teacher> listTeachersByFilters(double qualificationFrom, double qualificationTo, Long rigurosityId, Long courseId, String fullNameTeacher){
        //prefix para el nombre, las coincidencias
        List<Teacher> l1=new ArrayList<>();
        if(!fullNameTeacher.isEmpty() || fullNameTeacher != null){
            l1=teacherRepository.findTeacherByFullnameStartingWith(fullNameTeacher);
        }
        else{
            l1=listTeacher();
        }
        //en front siempre va a estar seteado de 1 a 5, se va poner que este campo nunca va a estar vacio
        if((qualificationFrom<qualificationTo) && qualificationFrom >= 1 && qualificationTo <= 5){
            for (Teacher teacher:listTeacher()
            ) {
                if(teacher.getQualification()<qualificationFrom || teacher.getQualification()>qualificationTo){
                    l1.remove(teacher);
                }
            }
        }
        //en front siempre debe escogerse una rigurosidad
        if(rigurosityId != null){
            for (Teacher teacher:listTeacher()
            ) {
                if(teacher.getRigurosity().getId() != rigurosityId){
                    l1.remove(teacher);
                }
            }
        }
        if(courseId != null){
            for (Teacher teacher:listTeacher()
            ) {
                boolean flag = false;
                for (Course course:teacher.getCoursearray()
                ) {
                    if(course.getId().equals(courseId)){
                        flag = true;
                    }
                }
                if(!flag){
                    l1.remove(teacher);
                }
            }
        }
        return l1;
    }
    //llamarlo cada vez que se ingrese una reseña, para que se actualice según convenga la rigurosidad del profesor
    public void SetRigurosityTeacher(Teacher teacher){
         int cbaja=0, cmedia=0, calta=0, cvariable=0;
        for (TeacherReview tea :teacherReviewRepository.findTeacherReviewsByTeacher_Id(teacher.getId())) {
            switch (tea.getRigurosityTitle()){
                case "Baja":
                    cbaja++;
                    break;
                case "Media":
                    cmedia++;
                    break;
                case "Alta":
                    calta++;
                    break;
                case "Variable":
                    cvariable++;
                    break;
            }
        }
        List<Integer> l1 = new ArrayList<>();
        l1.add(cbaja); l1.add(cmedia); l1.add(calta); l1.add(cvariable);
        int max = 0;
        for (Integer i:l1) {
            if(max<i){
                max = i;
            }
        }
        if (max != 0){
            if(max == cbaja){
                teacher.setRigurosity(rigurosityRepository.findRigurosityByName("Baja"));
            }
            else if(max == cmedia){
                teacher.setRigurosity(rigurosityRepository.findRigurosityByName("Media"));
            }
            else if(max == calta){
                teacher.setRigurosity(rigurosityRepository.findRigurosityByName("Alta"));
            }
            else if(max == cvariable){
                teacher.setRigurosity(rigurosityRepository.findRigurosityByName("Variable"));
            }
        }
    }

    //implementar filtro por rigurosidad (se debe pasar solamente el id) para listar todos los profesores que tengan esa rigurosidad
    public List<Teacher> listTeachersByRigurosity(String rigurosityName){
        // id:1 -> Baja ; id:2-> Media ; id:3-> Alta ; id:4-> Variable; id:5-> No definido
        List<Teacher> l1 = new ArrayList<>();
        for (Teacher teacher:listTeacher()) {
             switch (rigurosityName)
             {
                 case "Baja":
                     if(teacher.getRigurosity().getId() == 1){
                         l1.add(teacher);
                     }
                     break;
                    case "Media":
                        if(teacher.getRigurosity().getId() == 2){
                            l1.add(teacher);
                        }
                        break;
                    case "Alta":
                        if(teacher.getRigurosity().getId() == 3){
                            l1.add(teacher);
                        }
                        break;
                    case "Variable":
                        if(teacher.getRigurosity().getId() == 4){
                            l1.add(teacher);
                        }
                        break;
                    case "No definido":
                        if(teacher.getRigurosity().getId() == 5){
                            l1.add(teacher);
                        }
                        break;
             }
        }
        return l1;
    }

    //Implementar filtro de profesores por curso que dicta, se pasará el id del curso como parámetro, si un docente cuenta con un curso con el mismo ID se ingresará a una lista de teachers
    public List<Teacher> listTeachersByCourse(Long courseId){
        List<Teacher> l1 = new ArrayList<>();
        for (Teacher teacher:listTeacher()) {
            for (Course course:teacher.getCoursearray()) {
                if(course.getId().equals(courseId)){
                    l1.add(teacher);
                }
            }
        }
        return l1;
    }

    //Implementar filtro de docente que en su qualification tenga como mínimo un parámetro de ingreso hasta como máximo otro parámetro de ingreso.

    public List<Teacher> listTeachersByQualification(double qualificationFrom, double qualificationTo){
        List<Teacher> l1=new ArrayList<>();
        if((qualificationFrom<qualificationTo) && qualificationFrom >= 1 && qualificationTo <= 5){
            for (Teacher teacher:listTeacher()
            ) {
                if(teacher.getQualification()>=qualificationFrom && teacher.getQualification()<=qualificationTo){
                    l1.add(teacher);
                }
            }
        }
        return l1;
    }

    public Teacher getTeacherByID(Long ID){
        Teacher teacher = teacherRepository.getTeacherById(ID);
        CalcQualification(teacher);
        return teacher;
    }
}
