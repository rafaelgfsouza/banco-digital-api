package com.rafael.bancodigital.controller;

import com.rafael.bancodigital.dto.ContaExibicaoDTO;
import com.rafael.bancodigital.model.Conta;
import com.rafael.bancodigital.repository.ContaRepository;
import com.rafael.bancodigital.service.ContaService;
import com.rafael.bancodigital.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService; // Adicione esta linha!

    @GetMapping
    public List<ContaExibicaoDTO> listar() {
        // Converte a entidade do banco para o DTO seguro (sem senha/dados sensíveis)
        return repository.findAll().stream()
                .map(conta -> new ContaExibicaoDTO(
                        conta.getId(),
                        conta.getNumeroConta(),
                        conta.getSaldo(),
                        conta.getCliente().getNome()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String criarConta(@RequestBody Conta conta) {
        // Criptografa a senha antes de salvar no banco de dados
        String senhaCriptografada = encoder.encode(conta.getSenha());
        conta.setSenha(senhaCriptografada);

        repository.save(conta);
        return "Conta criada com sucesso e senha criptografada!";
    }

    @PostMapping("/transferir")
    public String transferir(
            @RequestHeader("Authorization") String token,
            @RequestParam String numeroDestino,
            @RequestParam BigDecimal valor) {

        // 1. O TokenService lê o número da conta de quem está logado
        String numeroOrigem = tokenService.getSubject(token);

        // 2. Chamamos o Service para fazer a mágica no banco
        contaService.transferirComToken(numeroOrigem, numeroDestino, valor);

        return "Transferência de R$ " + valor + " realizada com sucesso para " + numeroDestino;
    }
}