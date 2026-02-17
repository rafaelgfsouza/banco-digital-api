package com.rafael.bancodigital.repository;

import com.rafael.bancodigital.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    // Busca todas as transações onde a conta foi origem OU destino
    List<Transacao> findByOrigemIdOrDestinoIdOrderByDataHoraDesc(Long origemId, Long destinoId);
}