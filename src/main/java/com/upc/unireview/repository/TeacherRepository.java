package com.upc.unireview.repository;

import com.upc.unireview.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

   public List<Teacher> findTeacherByFullnameStartingWith(String prefix);
   public Teacher getTeacherById(Long ID);
}
