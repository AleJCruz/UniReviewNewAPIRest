package com.upc.unireview.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.upc.unireview.entities.Course;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Profesor")
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)//nombre único por profesor
    private String fullname;
    private transient double qualification;
    @Column(length = 150, nullable = true)
    private String summary;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "profesor_curso",
            joinColumns = @JoinColumn(name = "Profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "Curso_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"Profesor_id", "Curso_id"})
    )
    private List<Course> coursearray = new ArrayList<>();
    //une a la tabla rigurosidad
    //como hacer que no sea obligatorio poner rigurosidad, preguntar profe
    @ManyToOne(targetEntity = Rigurosity.class )
    @JoinColumn(name = "rigurosidad_id", referencedColumnName = "id")
    private Rigurosity rigurosity;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Image.class )
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

}
