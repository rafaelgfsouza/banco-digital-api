package com.rafael.bancodigital.dto;

public class LoginDTO {
    private String numeroConta;
    private String senha;

    // Getters e Setters
    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}