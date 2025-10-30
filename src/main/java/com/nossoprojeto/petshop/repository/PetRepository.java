package com.nossoprojeto.petshop.repository;

import com.nossoprojeto.petshop.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByTutorId(Long tutorId); // Para filtro por tutor
}