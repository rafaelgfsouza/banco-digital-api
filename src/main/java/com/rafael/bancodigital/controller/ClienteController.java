package com.rafael.bancodigital.controller;

import com.rafael.bancodigital.dto.CadastroDTO;
import com.rafael.bancodigital.model.Cliente;
import com.rafael.bancodigital.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*") // Importante para o seu HTML/JS conseguir acessar
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody CadastroDTO dto) {
        // O service salva o cliente (que já vem com a conta dentro)
        Cliente clienteSalvo = clienteService.cadastrarCliente(dto);

        // Retorna o cliente salvo. O JSON terá o campo "conta" com o "numeroConta"
        return ResponseEntity.ok(clienteSalvo);
    }
}