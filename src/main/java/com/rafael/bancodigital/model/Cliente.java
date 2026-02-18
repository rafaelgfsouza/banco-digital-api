package com.rafael.bancodigital.model;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    // fetch = FetchType.EAGER: Garante que a conta venha junto com o cliente no login
    // cascade = CascadeType.ALL: Salva a conta automaticamente ao salvar o cliente
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Conta conta;

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
}