package com.upc.unireview.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviewProfesor")
@Entity
public class TeacherReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate reviewdate;
    @Column(length = 3000,nullable = false)
    private String description;
    @Column
    private int score;
    @ManyToOne(targetEntity = Teacher.class) //se une a profesor
    @JoinColumn(name = "profesor_id", referencedColumnName = "id")
    private Teacher teacher;
    @ManyToOne(targetEntity = User.class) //se une a usuario
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, length = 50)
    private String rigurosityTitle;
    //pueden ser 4 tipos de rigurosidad:variable, baja, moderada, alta y sin definir
}
