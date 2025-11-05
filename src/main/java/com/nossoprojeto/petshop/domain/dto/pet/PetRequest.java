// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/pet/PetRequest.java
package com.nossoprojeto.petshop.domain.dto.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PetRequest(
    @NotBlank String nome,
    @NotBlank String especie,
    String raca,
    LocalDate dataNascimento,
    @NotNull Long tutorId // Apenas o ID
) {}