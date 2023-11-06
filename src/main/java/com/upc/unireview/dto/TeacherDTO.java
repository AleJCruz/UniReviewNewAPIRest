package com.upc.unireview.dto;
import com.upc.unireview.entities.Course;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherDTO {
    private Long id;
    private String fullname;
    private transient double qualification;
    private String summary;
    private List<CourseDTO> coursesarray= new ArrayList<>();
    private RigurosityDTO rigurosity;
    private ImageDTO image;
}
