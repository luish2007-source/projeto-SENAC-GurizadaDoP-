// Caminho: src/main/java/com/nossoprojeto/petshop/controller/AtendimentoController.java
package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.dto.atendimento.AtendimentoRequest;
import com.nossoprojeto.petshop.domain.dto.atendimento.AtendimentoResponse;
import com.nossoprojeto.petshop.service.AtendimentoService; // IMPORTA O SERVICE
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service; // INJETA O SERVICE

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<AtendimentoResponse>> listar(Pageable pageable) {
        Page<AtendimentoResponse> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> buscar(@PathVariable Long id) {
        AtendimentoResponse dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoRequest dto, UriComponentsBuilder uriBuilder) {
        AtendimentoResponse response = service.save(dto);
        URI uri = uriBuilder.path("/api/atendimentos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtendimentoRequest dto) {
        AtendimentoResponse response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}