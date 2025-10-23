package com.nossoprojeto.petshop.dto.atendimento;

import java.time.LocalDateTime;

public class AtendimentoResponse {

    private Long id;
    private LocalDateTime dataHora;
    private String observacoes;

    // Dados do Pet
    private Long petId;
    private String petNome;

    // Dados do Serviço
    private Long servicoId;
    private String servicoDescricao;

    // Construtor padrão
    public AtendimentoResponse() {}

    // Construtor completo (opcional para facilitar conversão)
    public AtendimentoResponse(Long id, LocalDateTime dataHora, String observacoes,
                               Long petId, String petNome,
                               Long servicoId, String servicoDescricao) {
        this.id = id;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
        this.petId = petId;
        this.petNome = petNome;
        this.servicoId = servicoId;
        this.servicoDescricao = servicoDescricao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetNome() {
        return petNome;
    }

    public void setPetNome(String petNome) {
        this.petNome = petNome;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public String getServicoDescricao() {
        return servicoDescricao;
    }

    public void setServicoDescricao(String servicoDescricao) {
        this.servicoDescricao = servicoDescricao;
    }
}