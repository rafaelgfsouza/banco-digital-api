package com.rafael.bancodigital.service;

import com.rafael.bancodigital.model.Conta;
import com.rafael.bancodigital.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private PasswordEncoder encoder; // Precisamos dele para validar a senha criptografada

    @Transactional
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor, String senhaInformada) {
        Conta origem = repository.findById(idOrigem)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

        Conta destino = repository.findById(idDestino)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        // JEITO SÊNIOR: Validar senha usando BCrypt
        if (!encoder.matches(senhaInformada, origem.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }

        // Validação de saldo
        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        // Realiza a transferência
        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        repository.save(origem);
        repository.save(destino);
    }

    @Transactional
    public void transferirComToken(String numeroOrigem, String numeroDestino, BigDecimal valor) {
        // Busca as contas no banco pelo número
        Conta origem = repository.findAll().stream()
                .filter(c -> c.getNumeroConta().equals(numeroOrigem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        Conta destino = repository.findAll().stream()
                .filter(c -> c.getNumeroConta().equals(numeroDestino))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        // Atualiza os saldos
        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        // Salva no banco
        repository.save(origem);
        repository.save(destino);
    }

}