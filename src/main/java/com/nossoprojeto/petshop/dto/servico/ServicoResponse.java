package com.nossoprojeto.petshop.dto.servico;

import java.math.BigDecimal;

public class ServicoResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Integer duracaoMinutos;

    // Construtor padrão
    public ServicoResponse() {}

    // Construtor completo (opcional)
    public ServicoResponse(Long id, String descricao, BigDecimal valor, Integer duracaoMinutos) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.duracaoMinutos = duracaoMinutos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
}
