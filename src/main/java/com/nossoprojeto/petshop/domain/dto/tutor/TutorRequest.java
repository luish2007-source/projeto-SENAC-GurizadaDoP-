// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/tutor/TutorRequest.java
package com.nossoprojeto.petshop.domain.dto.tutor;

import jakarta.validation.constraints.NotBlank;

public record TutorRequest(
    @NotBlank String nome,
    @NotBlank String telefone,
    String email
) {}