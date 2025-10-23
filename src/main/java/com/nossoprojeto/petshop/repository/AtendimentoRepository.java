package com.nossoprojeto.petshop.repository;

import com.nossoprojeto.petshop.domain.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
}