package com.rafael.bancodigital.controller;

import com.rafael.bancodigital.dto.LoginDTO;
import com.rafael.bancodigital.model.Conta;
import com.rafael.bancodigital.repository.ContaRepository;
import com.rafael.bancodigital.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dados) {
        Conta conta = repository.findAll().stream()
                .filter(c -> c.getNumeroConta().equals(dados.getNumeroConta()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (encoder.matches(dados.getSenha(), conta.getSenha())) {
            // RETORNA O TOKEN AQUI!
            return tokenService.gerarToken(conta);
        } else {
            return "Senha incorreta!";
        }
    }
}