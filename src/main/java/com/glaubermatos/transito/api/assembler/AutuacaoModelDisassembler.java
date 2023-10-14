package com.glaubermatos.transito.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.glaubermatos.transito.api.model.input.AutuacaoInput;
import com.glaubermatos.transito.domain.model.Autuacao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AutuacaoModelDisassembler {
    
    private final ModelMapper modelMapper;

    public Autuacao toEntity(AutuacaoInput autuacaoInput) {
        return modelMapper.map(autuacaoInput, Autuacao.class);
    }
}
