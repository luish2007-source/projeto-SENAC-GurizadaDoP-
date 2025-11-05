// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/atendimento/AtendimentoResponse.java
package com.nossoprojeto.petshop.domain.dto.atendimento;

import com.nossoprojeto.petshop.domain.dto.pet.PetResponse; // DTO aninhado
import com.nossoprojeto.petshop.domain.dto.servico.ServicoResponse; // DTO aninhado
import com.nossoprojeto.petshop.domain.entity.Atendimento;
import java.time.LocalDateTime;

public record AtendimentoResponse(
    Long id,
    LocalDateTime dataHora,
    String observacoes,
    PetResponse pet,
    ServicoResponse servico
) {
    public static AtendimentoResponse fromEntity(Atendimento atendimento) {
        return new AtendimentoResponse(
            atendimento.getId(),
            atendimento.getDataHora(),
            atendimento.getObservacoes(),
            PetResponse.fromEntity(atendimento.getPet()), // Converte o Pet
            ServicoResponse.fromEntity(atendimento.getServico()) // Converte o Servico
        );
    }
}