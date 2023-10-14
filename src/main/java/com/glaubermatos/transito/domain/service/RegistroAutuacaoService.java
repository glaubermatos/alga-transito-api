package com.glaubermatos.transito.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaubermatos.transito.domain.model.Autuacao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroAutuacaoService {

    private final RegistroVeiculoService registroVeiculoService;
    
    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao autuacao) {
        var veiculo = registroVeiculoService.buscarVeiculoByIdOrElseThrow(veiculoId);

        return veiculo.adicionaAutuacao(autuacao);
    }
}
