// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/servico/ServicoResponse.java
package com.nossoprojeto.petshop.domain.dto.servico;

import com.nossoprojeto.petshop.domain.entity.Servico;
import java.math.BigDecimal;

public record ServicoResponse(
    Long id,
    String descricao,
    BigDecimal valor,
    Integer duracaoMinutos
) {
    public static ServicoResponse fromEntity(Servico servico) {
        return new ServicoResponse(
            servico.getId(),
            servico.getDescricao(),
            servico.getValor(),
            servico.getDuracaoMinutos()
        );
    }
}