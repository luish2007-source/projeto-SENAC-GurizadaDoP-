// Caminho: src/main/java/com/nossoprojeto/petshop/service/AtendimentoService.java
package com.nossoprojeto.petshop.service;

import com.nossoprojeto.petshop.domain.dto.atendimento.AtendimentoRequest;
import com.nossoprojeto.petshop.domain.dto.atendimento.AtendimentoResponse;
import com.nossoprojeto.petshop.domain.entity.Atendimento;
import com.nossoprojeto.petshop.domain.entity.Pet;
import com.nossoprojeto.petshop.domain.entity.Servico;
import com.nossoprojeto.petshop.repository.AtendimentoRepository;
import com.nossoprojeto.petshop.repository.PetRepository;
import com.nossoprojeto.petshop.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final PetRepository petRepository; // Para validar
    private final ServicoRepository servicoRepository; // Para validar

    public AtendimentoService(AtendimentoRepository atendimentoRepository, PetRepository petRepository, ServicoRepository servicoRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> findAll(Pageable pageable) {
        Page<Atendimento> page = atendimentoRepository.findAll(pageable);
        return page.map(AtendimentoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public AtendimentoResponse findById(Long id) {
        Atendimento entity = atendimentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atendimento não encontrado, ID: " + id));
        return AtendimentoResponse.fromEntity(entity);
    }

    @Transactional
    public AtendimentoResponse save(AtendimentoRequest dto) {
        // REGRA DE NEGÓCIO: Atendimento exige petId e servicoId válidos
        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado, ID: " + dto.petId()));
        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado, ID: " + dto.servicoId()));

        Atendimento entity = new Atendimento();
        copyDtoToEntity(entity, dto, pet, servico);
        
        entity = atendimentoRepository.save(entity);
        return AtendimentoResponse.fromEntity(entity);
    }

    @Transactional
    public AtendimentoResponse update(Long id, AtendimentoRequest dto) {
        Atendimento entity = atendimentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atendimento não encontrado, ID: " + id));
        
        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado, ID: " + dto.petId()));
        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado, ID: " + dto.servicoId()));

        copyDtoToEntity(entity, dto, pet, servico);
        
        entity = atendimentoRepository.save(entity);
        return AtendimentoResponse.fromEntity(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!atendimentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Atendimento não encontrado, ID: " + id);
        }
        atendimentoRepository.deleteById(id);
    }

    // Método helper
    private void copyDtoToEntity(Atendimento entity, AtendimentoRequest dto, Pet pet, Servico servico) {
        entity.setDataHora(dto.dataHora());
        entity.setObservacoes(dto.observacoes());
        entity.setPet(pet);
        entity.setServico(servico);
    }
}