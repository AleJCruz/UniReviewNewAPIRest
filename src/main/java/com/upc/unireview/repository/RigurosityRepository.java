package com.upc.unireview.repository;
import com.upc.unireview.entities.Rigurosity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RigurosityRepository extends JpaRepository<Rigurosity, Long> {
    public Rigurosity findRigurosityByName(String name);
    //dame un query para que cuente todas las rigurosidades que existen en la base de datos


}
