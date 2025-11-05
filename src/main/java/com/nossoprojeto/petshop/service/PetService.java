// Caminho: src/main/java/com/nossoprojeto/petshop/service/PetService.java
package com.nossoprojeto.petshop.service;

import com.nossoprojeto.petshop.domain.dto.pet.PetRequest;
import com.nossoprojeto.petshop.domain.dto.pet.PetResponse;
import com.nossoprojeto.petshop.domain.entity.Pet;
import com.nossoprojeto.petshop.domain.entity.Tutor;
import com.nossoprojeto.petshop.repository.PetRepository;
import com.nossoprojeto.petshop.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository; // Para validar o Tutor

    public PetService(PetRepository petRepository, TutorRepository tutorRepository) {
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
    }

    @Transactional(readOnly = true)
    public Page<PetResponse> findAll(Pageable pageable) {
        Page<Pet> page = petRepository.findAll(pageable);
        return page.map(PetResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public PetResponse findById(Long id) {
        Pet entity = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado, ID: " + id));
        return PetResponse.fromEntity(entity);
    }

    @Transactional
    public PetResponse save(PetRequest dto) {
        // REGRA DE NEGÓCIO: Pet só pode ser criado se o tutorId existir
        Tutor tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado, ID: " + dto.tutorId()));

        Pet entity = new Pet();
        copyDtoToEntity(entity, dto, tutor);
        
        entity = petRepository.save(entity);
        return PetResponse.fromEntity(entity);
    }

    @Transactional
    public PetResponse update(Long id, PetRequest dto) {
        Pet entity = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado, ID: " + id));
        
        Tutor tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado, ID: " + dto.tutorId()));

        copyDtoToEntity(entity, dto, tutor);
        
        entity = petRepository.save(entity);
        return PetResponse.fromEntity(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet não encontrado, ID: " + id);
        }
        // Lógica de negócio: Não deixar apagar pet se ele tiver atendimentos (opcional)
        // if (atendimentoRepository.existsByPetId(id)) {
        //     throw new RuntimeException("Não pode deletar pet que possui atendimentos.");
        // }
        petRepository.deleteById(id);
    }

    // Método helper para não repetir código em save/update
    private void copyDtoToEntity(Pet entity, PetRequest dto, Tutor tutor) {
        entity.setNome(dto.nome());
        entity.setEspecie(dto.especie());
        entity.setRaca(dto.raca());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setTutor(tutor); // Associa o tutor encontrado
    }
}