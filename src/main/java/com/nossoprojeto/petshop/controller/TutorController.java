// Seu controller corrigido
package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.dto.tutor.TutorRequest;
import com.nossoprojeto.petshop.domain.dto.tutor.TutorResponse;
import com.nossoprojeto.petshop.service.TutorService; // Importa o SERVICE
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// (imports...)

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    // 1. INJETA O SERVICE (não o repository)
    private final TutorService service;

    public TutorController(TutorService service) {
        this.service = service;
    }

    // 2. USA PAGINAÇÃO (Pageable) e DTO (TutorResponse)
    @GetMapping
    public ResponseEntity<Page<TutorResponse>> listar(Pageable pageable) {
        Page<TutorResponse> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    // 3. USA DTO (TutorResponse)
    @GetMapping("/{id}")
    public ResponseEntity<TutorResponse> buscar(@PathVariable Long id) {
        TutorResponse dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    // 4. USA DTO (TutorRequest) e @Valid (não esqueça)
    @PostMapping
    public ResponseEntity<TutorResponse> criar(@RequestBody TutorRequest dto) { // @Valid TutorRequest dto
        TutorResponse response = service.save(dto);
        // Retorna 201 Created
        return ResponseEntity.status(201).body(response); 
    }

    // ... incluir PUT e DELETE, sempre usando o Service e DTOs
}