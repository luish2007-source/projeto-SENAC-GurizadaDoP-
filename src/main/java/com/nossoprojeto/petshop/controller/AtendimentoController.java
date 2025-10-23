package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.entity.Atendimento;
import com.nossoprojeto.petshop.repository.AtendimentoRepository;
import com.nossoprojeto.petshop.repository.PetRepository;
import com.nossoprojeto.petshop.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoRepository atendimentoRepo;
    private final PetRepository petRepo;
    private final ServicoRepository servicoRepo;

    public AtendimentoController(AtendimentoRepository atendimentoRepo, PetRepository petRepo, ServicoRepository servicoRepo) {
        this.atendimentoRepo = atendimentoRepo;
        this.petRepo = petRepo;
        this.servicoRepo = servicoRepo;
    }

    @GetMapping
    public List<Atendimento> listar() {
        return atendimentoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Atendimento buscar(@PathVariable Long id) {
        return atendimentoRepo.findById(id).orElseThrow();
    }

    @PostMapping
    public Atendimento criar(@RequestBody Atendimento atendimento) {
        atendimento.setId(null); // garante que será criado um novo registro
        // Regra: petId e servicoId devem existir
        petRepo.findById(atendimento.getPet().getId()).orElseThrow();
        servicoRepo.findById(atendimento.getServico().getId()).orElseThrow();
        return atendimentoRepo.save(atendimento);
    }

    @PutMapping("/{id}")
    public Atendimento atualizar(@PathVariable Long id, @RequestBody Atendimento atendimento) {
        Atendimento atual = atendimentoRepo.findById(id).orElseThrow();
        atual.setDataHora(atendimento.getDataHora());
        atual.setPet(atendimento.getPet());
        atual.setServico(atendimento.getServico());
        atual.setObservacoes(atendimento.getObservacoes());
        return atendimentoRepo.save(atual);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {
        atendimentoRepo.deleteById(id);
    }
}
