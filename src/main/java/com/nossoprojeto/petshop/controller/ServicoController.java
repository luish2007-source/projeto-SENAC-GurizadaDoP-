// Caminho: src/main/java/com/nossoprojeto/petshop/controller/ServicoController.java
package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.dto.servico.ServicoRequest;
import com.nossoprojeto.petshop.domain.dto.servico.ServicoResponse;
import com.nossoprojeto.petshop.service.ServicoService; // IMPORTA O SERVICE
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService service; // INJETA O SERVICE

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ServicoResponse>> listar(Pageable pageable) {
        Page<ServicoResponse> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscar(@PathVariable Long id) {
        ServicoResponse dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ServicoResponse> criar(@Valid @RequestBody ServicoRequest dto, UriComponentsBuilder uriBuilder) {
        ServicoResponse response = service.save(dto);
        URI uri = uriBuilder.path("/api/servicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ServicoRequest dto) {
        ServicoResponse response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}