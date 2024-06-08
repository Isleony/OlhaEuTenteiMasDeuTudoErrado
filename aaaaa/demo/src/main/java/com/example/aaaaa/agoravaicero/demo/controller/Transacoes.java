package com.example.aaaaa.agoravaicero.demo.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Transacoes {
    private List<Transacao> transacoes = new ArrayList<>();
    @Setter
    @Getter
    private Transacao transacao;

    @PostMapping("/transacao")
    public ResponseEntity<Void> receberTransacao(@RequestBody Transacao transacao) {
        this.transacao = transacao;
        if ((transacao.getValor() == null) || (transacao.getDataHora() == null) ||
                transacao.getDataHora().isAfter(OffsetDateTime.now().toOffsetTime()) ||
                (transacao.getValor() < 0)) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            transacoes.add(transacao);
            return ResponseEntity.ok().build();
        }

    }
    @DeleteMapping("/trasacao")
    public ResponseEntity<Void> limparTransacoes(){
        transacoes.clear();
        return ResponseEntity.ok().build();
    }

}
