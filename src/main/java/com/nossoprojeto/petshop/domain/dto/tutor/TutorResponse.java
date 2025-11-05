// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/tutor/TutorResponse.java
package com.nossoprojeto.petshop.domain.dto.tutor;

import com.nossoprojeto.petshop.domain.entity.Tutor;

public record TutorResponse(
    Long id,
    String nome,
    String telefone,
    String email
) {
    public static TutorResponse fromEntity(Tutor tutor) {
        return new TutorResponse(
            tutor.getId(),
            tutor.getNome(),
            tutor.getTelefone(),
            tutor.getEmail()
        );
    }
}