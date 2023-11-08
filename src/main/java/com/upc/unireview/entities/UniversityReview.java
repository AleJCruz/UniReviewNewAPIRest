package com.upc.unireview.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviewUniversidad")
@Entity
public class UniversityReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate reviewdate;
    @Column(length = 500,nullable = false)
    private String relatedCareer;
    @Column(length = 3000,nullable = false)
    private String description;
    @Column
    private int score;
    @ManyToOne(targetEntity = University.class) //se une a universidad
    @JoinColumn(name = "universidad_id", referencedColumnName = "id")
    private University university;
    @ManyToOne(targetEntity = User.class) //se une a usuario
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private User user;
}
