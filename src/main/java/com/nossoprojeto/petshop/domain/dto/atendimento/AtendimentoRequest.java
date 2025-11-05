// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/atendimento/AtendimentoRequest.java
package com.nossoprojeto.petshop.domain.dto.atendimento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AtendimentoRequest(
    @NotNull @FutureOrPresent LocalDateTime dataHora,
    @NotNull Long petId,
    @NotNull Long servicoId,
    String observacoes
) {}