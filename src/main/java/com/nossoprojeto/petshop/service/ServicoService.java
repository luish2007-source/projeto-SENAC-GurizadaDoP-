// Caminho: src/main/java/com/nossoprojeto/petshop/service/ServicoService.java
package com.nossoprojeto.petshop.service;

import com.nossoprojeto.petshop.domain.dto.servico.ServicoRequest;
import com.nossoprojeto.petshop.domain.dto.servico.ServicoResponse;
import com.nossoprojeto.petshop.domain.entity.Servico;
import com.nossoprojeto.petshop.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ServicoResponse> findAll(Pageable pageable) {
        Page<Servico> page = repository.findAll(pageable);
        return page.map(ServicoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public ServicoResponse findById(Long id) {
        Servico entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado, ID: " + id));
        return ServicoResponse.fromEntity(entity);
    }

    @Transactional
    public ServicoResponse save(ServicoRequest dto) {
        Servico entity = new Servico();
        entity.setDescricao(dto.descricao());
        entity.setValor(dto.valor());
        entity.setDuracaoMinutos(dto.duracaoMinutos());
        
        entity = repository.save(entity);
        return ServicoResponse.fromEntity(entity);
    }

    @Transactional
    public ServicoResponse update(Long id, ServicoRequest dto) {
        Servico entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado, ID: " + id));
        
        entity.setDescricao(dto.descricao());
        entity.setValor(dto.valor());
        entity.setDuracaoMinutos(dto.duracaoMinutos());

        entity = repository.save(entity);
        return ServicoResponse.fromEntity(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Serviço não encontrado, ID: " + id);
        }
        // Lógica de negócio: Não deixar apagar serviço se ele tiver atendimentos (opcional)
        // if (atendimentoRepository.existsByServicoId(id)) {
        //     throw new RuntimeException("Não pode deletar serviço que possui atendimentos.");
        // }
        repository.deleteById(id);
    }
}