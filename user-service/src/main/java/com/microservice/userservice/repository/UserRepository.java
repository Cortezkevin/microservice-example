package com.microservice.userservice.repository;

import com.microservice.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository anota las clases en la capa de persistencia, que actuará como repositorio de la base de datos.
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
