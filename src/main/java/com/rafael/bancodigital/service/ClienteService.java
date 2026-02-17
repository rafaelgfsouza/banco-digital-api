package com.rafael.bancodigital.service;

import com.rafael.bancodigital.dto.CadastroDTO;
import com.rafael.bancodigital.model.Cliente;
import com.rafael.bancodigital.model.Conta;
import com.rafael.bancodigital.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Cliente cadastrarCliente(CadastroDTO dados) {
        // 1. Cria o objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setNome(dados.nome());
        cliente.setCpf(dados.cpf());
        cliente.setEmail(dados.email());

        // 2. Cria a conta vinculada
        Conta novaConta = new Conta();
        //novaConta.setSaldo(BigDecimal.ZERO);
        novaConta.setSaldo(new BigDecimal("100.00")); // para testar transferências
        novaConta.setNumeroConta(gerarNumeroConta());
        novaConta.setSenha(encoder.encode(dados.senha())); // Criptografa a senha do DTO

        // 3. Vincula um ao outro
        novaConta.setCliente(cliente);
        cliente.setConta(novaConta);

        return clienteRepository.save(cliente);
    }

    private String gerarNumeroConta() {
        Random random = new Random();
        return (random.nextInt(9000) + 1000) + "-" + (random.nextInt(90) + 10);
    }
}