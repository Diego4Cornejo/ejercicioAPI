package com.ejercicio.apiusuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejercicio.apiusuarios.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long>{

    List<User> findByEmail(String email);
    
}