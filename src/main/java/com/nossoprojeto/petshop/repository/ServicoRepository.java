package com.nossoprojeto.petshop.repository;

import com.nossoprojeto.petshop.domain.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}