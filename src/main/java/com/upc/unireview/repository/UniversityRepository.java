package com.upc.unireview.repository;
import com.upc.unireview.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    public List<University> findAllByNameStartingWith(String prefix);
    //listar todas las universidades por campus


}
