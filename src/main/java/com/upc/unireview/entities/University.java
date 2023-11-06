package com.upc.unireview.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Universidad")
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100,nullable = false)
    private String campus;
    @Column(nullable = false)
    private double pension;
    @Column(nullable = false)
    private boolean havepostgraduate;
    @Column(nullable = false)
    private boolean haveundergraduate;
    @Column(nullable = false)
    private boolean havepeoplewhowork;
    private transient double qualification;



    //link que lleve a la página de matrícula de la universidad, esta info tiene que ponerla el admin
    //crear columna String
    @Column(nullable = true, length = 255)
    private String enrollmentLink;
    //hacer que cuando se cree la entidad havepostgraduate, haveundergraduate, havepeoplewhowork, outstandingcareer
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Image.class )
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

}
