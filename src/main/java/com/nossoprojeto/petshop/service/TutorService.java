// Caminho: src/main/java/com/nossoprojeto/petshop/service/TutorService.java
package com.nossoprojeto.petshop.service;

import com.nossoprojeto.petshop.domain.dto.tutor.TutorRequest;
import com.nossoprojeto.petshop.domain.dto.tutor.TutorResponse;
import com.nossoprojeto.petshop.domain.entity.Tutor;
import com.nossoprojeto.petshop.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorService {

    private final TutorRepository repository;

    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<TutorResponse> findAll(Pageable pageable) {
        Page<Tutor> page = repository.findAll(pageable);
        return page.map(TutorResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public TutorResponse findById(Long id) {
        Tutor entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado, ID: " + id));
        return TutorResponse.fromEntity(entity);
    }

    @Transactional
    public TutorResponse save(TutorRequest dto) {
        Tutor entity = new Tutor();
        entity.setNome(dto.nome());
        entity.setTelefone(dto.telefone());
        entity.setEmail(dto.email());
        
        entity = repository.save(entity);
        return TutorResponse.fromEntity(entity);
    }

    @Transactional
    public TutorResponse update(Long id, TutorRequest dto) {
        Tutor entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado, ID: " + id));
        
        entity.setNome(dto.nome());
        entity.setTelefone(dto.telefone());
        entity.setEmail(dto.email());

        entity = repository.save(entity);
        return TutorResponse.fromEntity(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tutor não encontrado, ID: " + id);
        }
        // Lógica de negócio: Não deixar apagar tutor se ele tiver pets (opcional)
        // if (petRepository.existsByTutorId(id)) {
        //     throw new RuntimeException("Não pode deletar tutor que possui pets.");
        // }
        repository.deleteById(id);
    }
}