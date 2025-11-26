// Caminho: src/main/java/com/nossoprojeto/petshop/domain/dto/pet/PetResponse.java
package com.nossoprojeto.petshop.domain.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nossoprojeto.petshop.domain.dto.tutor.TutorResponse; // Importa o DTO de Tutor
import com.nossoprojeto.petshop.domain.entity.Pet;
import java.time.LocalDate;

public record PetResponse(
    Long id,
    String nome,
    String especie,
    String raca,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dataNascimento,
    TutorResponse tutor // Retorna o objeto Tutor completo (como DTO)
) {
    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(
            pet.getId(),
            pet.getNome(),
            pet.getEspecie(),
            pet.getRaca(),
            pet.getDataNascimento(),
            TutorResponse.fromEntity(pet.getTutor()) // Converte o Tutor aninhado
        );
    }
}