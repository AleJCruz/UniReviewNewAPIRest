package com.upc.unireview.interfaceservice;
import com.upc.unireview.entities.Course;
import java.util.List;

public interface CourseService {
    public Course register(Course course);
    public Course updateCourse(Course course) throws Exception;
    public Course deleteCourse(Long id) throws Exception;
    public List<Course> listCourses();
}
