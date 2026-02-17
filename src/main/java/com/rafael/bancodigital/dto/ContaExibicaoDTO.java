package com.rafael.bancodigital.dto;

import java.math.BigDecimal;

public class ContaExibicaoDTO {
    private final Long id;
    private final String numeroConta;
    private final BigDecimal saldo;
    private final String nomeTitular;

    // Construtor para facilitar a conversão
    public ContaExibicaoDTO(Long id, String numeroConta, BigDecimal saldo, String nomeTitular) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.nomeTitular = nomeTitular;
    }

    // Apenas Getters (DTO de exibição costuma ser imutável)
    public Long getId() { return id; }
    public String getNumeroConta() { return numeroConta; }
    public BigDecimal getSaldo() { return saldo; }
    public String getNomeTitular() { return nomeTitular; }
}