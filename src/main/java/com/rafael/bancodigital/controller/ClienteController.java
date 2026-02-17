package com.rafael.bancodigital.controller;

import com.rafael.bancodigital.dto.CadastroDTO;
import com.rafael.bancodigital.model.Cliente;
import com.rafael.bancodigital.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public String cadastrar(@RequestBody CadastroDTO dados) {
        Cliente novoCliente = clienteService.cadastrarCliente(dados);
        return "Cliente cadastrado com sucesso! Conta Gerada: " + novoCliente.getConta().getNumeroConta();
    }
}