package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.entity.Tutor;
import com.nossoprojeto.petshop.repository.TutorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    private final TutorRepository repo;

    public TutorController(TutorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Tutor> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Tutor buscar(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Tutor criar(@RequestBody Tutor tutor) {
        tutor.setId(null); // garante que será criado um novo registro
        return repo.save(tutor);
    }

    @PutMapping("/{id}")
    public Tutor atualizar(@PathVariable Long id, @RequestBody Tutor tutor) {
        Tutor atual = repo.findById(id).orElseThrow();
        atual.setNome(tutor.getNome());
        atual.setTelefone(tutor.getTelefone());
        atual.setEmail(tutor.getEmail());
        return repo.save(atual);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}