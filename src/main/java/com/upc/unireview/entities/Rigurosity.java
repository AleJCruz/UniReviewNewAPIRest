package com.upc.unireview.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity(name = "Rigurosity")
public class Rigurosity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;

    //pueden ser 4 tipos de rigurosidad:variable, baja, moderada, alta y sin definir
    //id:1 -> Baja , id:2 ->Moderada, id:3 -> Alta, id:4 -> Variable, id:5 -> Sin definir
    //Como hacer para que se creen esos 4 valores al iniciar la aplicación o al crear la base de datos
    public Rigurosity(Long ID, String name){
        this.id=ID;
        this.name=name;
    }
    public Rigurosity(String name) {
        this.name = name;
    }
}
