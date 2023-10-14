package com.glaubermatos.transito.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.glaubermatos.transito.api.model.AutuacaoModel;
import com.glaubermatos.transito.domain.model.Autuacao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AutuacaoModelAssembler {
    
    private final ModelMapper modelMapper;

    public AutuacaoModel toModel(Autuacao autuacao) {
        return modelMapper.map(autuacao, AutuacaoModel.class);
    }

    public List<AutuacaoModel> toCollectionModel(List<Autuacao> autuacoes) {
        return autuacoes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
