package com.rafael.bancodigital.controller;

import com.rafael.bancodigital.dto.LoginDTO;
import com.rafael.bancodigital.model.Conta;
import com.rafael.bancodigital.repository.ContaRepository;
import com.rafael.bancodigital.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<String> login(@RequestBody LoginDTO dados) {
        // Busca direto no banco pelo número da conta (mais rápido e evita o erro)
        // Se o seu DTO usa getCpf(), mude para buscar pelo CPF no ClienteRepository!
        Conta conta = repository.findByNumeroConta(dados.getNumeroConta())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (encoder.matches(dados.getSenha(), conta.getSenha())) {
            // Gera e retorna o token
            String token = tokenService.gerarToken(conta);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta!");
        }
    }
}