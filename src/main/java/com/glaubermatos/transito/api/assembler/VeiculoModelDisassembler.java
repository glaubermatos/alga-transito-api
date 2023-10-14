package com.glaubermatos.transito.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.glaubermatos.transito.api.model.input.VeiculoInputModel;
import com.glaubermatos.transito.domain.model.Veiculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Component
public class VeiculoModelDisassembler {
    
    private final ModelMapper modelMapper;

    public Veiculo toEntityModel(VeiculoInputModel veiculoInput) {
        return modelMapper.map(veiculoInput, Veiculo.class);
    }
}
