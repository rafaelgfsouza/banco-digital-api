package com.rafael.bancodigital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroConta;

    private BigDecimal saldo;

    @JsonIgnore
    private String senha;

    // @JsonIgnore aqui evita que o JSON tente desenhar o cliente dentro da conta
    // e entre em loop infinito (o erro 500 que deu antes)
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}