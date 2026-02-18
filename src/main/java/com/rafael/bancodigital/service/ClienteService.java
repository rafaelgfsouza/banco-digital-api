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
        // Definindo saldo inicial de 100 para testes
        novaConta.setSaldo(new BigDecimal("100.00"));
        novaConta.setNumeroConta(gerarNumeroConta());

        // MUITA ATENÇÃO AQUI: Verifique se o login busca a senha no Cliente ou na Conta.
        // Se o seu AuthController busca no cliente, precisamos de um campo senha lá.
        // Como o erro é "Conta não encontrada", vamos garantir o vínculo:
        novaConta.setSenha(encoder.encode(dados.senha()));

        // 3. Vinculo bi-direcional (Essencial para o JPA/Hibernate)
        novaConta.setCliente(cliente);
        cliente.setConta(novaConta);

        // 4. Salva o cliente (o CascadeType.ALL no Cliente vai salvar a Conta automaticamente)
        return clienteRepository.save(cliente);
    }

    private String gerarNumeroConta() {
        Random random = new Random();
        // Gera um formato tipo 1234-56
        return (random.nextInt(9000) + 1000) + "-" + (random.nextInt(90) + 10);
    }
}