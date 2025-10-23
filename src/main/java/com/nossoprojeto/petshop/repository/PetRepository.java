package com.nossoprojeto.petshop.repository;

import com.nossoprojeto.petshop.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}