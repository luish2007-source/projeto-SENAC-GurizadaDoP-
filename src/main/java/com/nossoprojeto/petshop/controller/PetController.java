package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.entity.Pet;
import com.nossoprojeto.petshop.repository.PetRepository;
import com.nossoprojeto.petshop.repository.TutorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRepository petRepo;
    private final TutorRepository tutorRepo;

    public PetController(PetRepository petRepo, TutorRepository tutorRepo) {
        this.petRepo = petRepo;
        this.tutorRepo = tutorRepo;
    }

    @GetMapping
    public List<Pet> listar() {
        return petRepo.findAll();
    }

    @GetMapping("/{id}")
    public Pet buscar(@PathVariable Long id) {
        return petRepo.findById(id).orElseThrow();
    }

    @PostMapping
    public Pet criar(@RequestBody Pet pet) {
        pet.setId(null); // garante que será criado um novo registro
        // Regra: só cria se tutor existir
        tutorRepo.findById(pet.getTutor().getId()).orElseThrow();
        return petRepo.save(pet);
    }

    @PutMapping("/{id}")
    public Pet atualizar(@PathVariable Long id, @RequestBody Pet pet) {
        Pet atual = petRepo.findById(id).orElseThrow();
        atual.setNome(pet.getNome());
        atual.setEspecie(pet.getEspecie());
        atual.setRaca(pet.getRaca());
        atual.setDataNascimento(pet.getDataNascimento());
        atual.setTutor(pet.getTutor());
        return petRepo.save(atual);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {
        petRepo.deleteById(id);
    }
}
