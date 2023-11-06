package com.upc.unireview.repository;

import com.upc.unireview.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository  extends JpaRepository<Role,Long> {

}
