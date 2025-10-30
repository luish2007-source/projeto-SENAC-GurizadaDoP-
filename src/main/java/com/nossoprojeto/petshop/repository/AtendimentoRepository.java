package com.nossoprojeto.petshop.repository;

import com.nossoprojeto.petshop.domain.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findByDataHoraBetween(LocalDateTime start, LocalDateTime end); // Para filtro por data
}