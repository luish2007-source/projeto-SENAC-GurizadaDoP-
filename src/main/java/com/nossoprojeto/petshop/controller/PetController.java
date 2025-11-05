// Caminho: src/main/java/com/nossoprojeto/petshop/controller/PetController.java
package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.dto.pet.PetRequest;
import com.nossoprojeto.petshop.domain.dto.pet.PetResponse;
import com.nossoprojeto.petshop.service.PetService; // IMPORTA O SERVICE
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService service; // INJETA O SERVICE

    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PetResponse>> listar(Pageable pageable) {
        Page<PetResponse> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> buscar(@PathVariable Long id) {
        PetResponse dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PetResponse> criar(@Valid @RequestBody PetRequest dto, UriComponentsBuilder uriBuilder) {
        PetResponse response = service.save(dto);
        URI uri = uriBuilder.path("/api/pets/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> atualizar(@PathVariable Long id, @Valid @RequestBody PetRequest dto) {
        PetResponse response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}