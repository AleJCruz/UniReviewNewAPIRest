package com.upc.unireview.service;
import com.upc.unireview.entities.Course;
import com.upc.unireview.entities.Teacher;
import com.upc.unireview.entities.University;
import com.upc.unireview.interfaceservice.CourseService;
import com.upc.unireview.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public Course register(Course course){
        return courseRepository.save(course);
    }
    public Course updateCourse(Course course) throws Exception{
        courseRepository.findById(course.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return courseRepository.save(course);
    }

    public Course deleteCourse(Long id) throws Exception{
        Course course = courseRepository.findById(id).orElseThrow(()->new Exception("No se encontro el curso"));
        courseRepository.delete(course);
        return course;
    }
    public List<Course> listCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByID(Long id){
        return courseRepository.findCourseById(id);
    }
}
