package com.rafael.bancodigital.repository;

import com.rafael.bancodigital.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aqui a mágica acontece: o JpaRepository já tem métodos como
    // save(), findAll(), deleteById() prontinhos para uso!
}