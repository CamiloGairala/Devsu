package com.devsu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.demo.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {


}
