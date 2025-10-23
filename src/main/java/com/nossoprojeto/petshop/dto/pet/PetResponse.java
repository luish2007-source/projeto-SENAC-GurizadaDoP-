package com.nossoprojeto.petshop.dto.pet;

import java.time.LocalDate;

public class PetResponse {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;

    // Dados do Tutor
    private Long tutorId;
    private String tutorNome;
    private String tutorTelefone;

    // Construtor padrão
    public PetResponse() {}

    // Construtor completo (opcional)
    public PetResponse(Long id, String nome, String especie, String raca, LocalDate dataNascimento,
                       Long tutorId, String tutorNome, String tutorTelefone) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutorId = tutorId;
        this.tutorNome = tutorNome;
        this.tutorTelefone = tutorTelefone;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public String getTutorNome() {
        return tutorNome;
    }

    public void setTutorNome(String tutorNome) {
        this.tutorNome = tutorNome;
    }

    public String getTutorTelefone() {
        return tutorTelefone;
    }

    public void setTutorTelefone(String tutorTelefone) {
        this.tutorTelefone = tutorTelefone;
    }
}