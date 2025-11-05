// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/servico/ServicoRequest.java
package com.nossoprojeto.petshop.domain.dto.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ServicoRequest(
    @NotBlank String descricao,
    @NotNull @Positive BigDecimal valor,
    @NotNull @Positive Integer duracaoMinutos
) {}