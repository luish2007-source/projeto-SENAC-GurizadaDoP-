package com.nossoprojeto.petshop.dto.servico;

import java.math.BigDecimal;

public class ServicoRequest {

    private String descricao;
    private BigDecimal valor;
    private Integer duracaoMinutos;

    // Construtor padrão
    public ServicoRequest() {}

    // Getters e Setters
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