package com.nossoprojeto.petshop.controller;

import com.nossoprojeto.petshop.domain.entity.Servico;
import com.nossoprojeto.petshop.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository repo;

    public ServicoController(ServicoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Servico> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Servico buscar(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Servico criar(@RequestBody Servico servico) {
        servico.setId(null); // garante que será criado um novo registro
        return repo.save(servico);
    }

    @PutMapping("/{id}")
    public Servico atualizar(@PathVariable Long id, @RequestBody Servico servico) {
        Servico atual = repo.findById(id).orElseThrow();
        atual.setDescricao(servico.getDescricao());
        atual.setValor(servico.getValor());
        atual.setDuracaoMinutos(servico.getDuracaoMinutos());
        return repo.save(atual);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}