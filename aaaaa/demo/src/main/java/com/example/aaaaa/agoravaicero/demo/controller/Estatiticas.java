package com.example.aaaaa.agoravaicero.demo.controller;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

@Getter
public class Estatiticas {
    @Setter
    private int count;
    @Setter
    private  double sum;
    @Setter
    private double avg;
    @Setter
    private  double min;
    @Setter
    private  double max;
    private Transacao transacao;

    public Estatiticas(int count, double sum, double avg, double min, double max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }
    @GetMapping("/estatistica")
    public ResponseEntity<Estatiticas> calcularEstatisticas(List<Transacao> transacoes) {
        int count = transacoes.size();
        double sum = transacoes.stream().mapToDouble(Transacao::getValor).sum();
        double avg = count > 0 ? sum / count : 0;
        double min = transacoes.stream().mapToDouble(Transacao::getValor).min().orElse(0);
        double max = transacoes.stream().mapToDouble(Transacao::getValor).max().orElse(0);

        Estatiticas estatisticas = new Estatiticas(count, sum, avg, min, max);
        return ResponseEntity.ok(estatisticas);
    }

    public boolean transacaoNosUltimos60Segundos(Transacao transacao){
        this.transacao = transacao;
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limiteInferior = agora.minusSeconds(60);
        return transacao.getDataHora().isAfter(limiteInferior.toOffsetTime()) && transacao.getDataHora().isBefore(OffsetTime.from(agora));
    }
}
