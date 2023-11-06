package com.upc.unireview.dto;

import com.upc.unireview.entities.University;
import com.upc.unireview.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class UniversityReviewDTO {
    private Long id;
    private LocalDate reviewdate;
    private String relatedCareer;
    private String description;
    private int score;
    private UniversityDTO university;
    private UserDTO user;
}
