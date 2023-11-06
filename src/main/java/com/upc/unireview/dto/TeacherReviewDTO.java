package com.upc.unireview.dto;

import com.upc.unireview.entities.Teacher;
import com.upc.unireview.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Data
public class TeacherReviewDTO {

    private Long id;
    private LocalDate reviewdate;
    private String description;
    private String rigurosityTitle;
    private int score;
    private TeacherDTO teacherDTO;
    private UserDTO user;
}
