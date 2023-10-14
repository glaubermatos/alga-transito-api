package com.glaubermatos.transito.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.glaubermatos.transito.api.model.VeiculoModel;
import com.glaubermatos.transito.domain.model.Veiculo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class VeiculoModelAssembler {
    
    private final ModelMapper modelMapper;

    public VeiculoModel toModel(Veiculo veiculo) {
        return modelMapper.map(veiculo, VeiculoModel.class);
    }

    public List<VeiculoModel> toCollectionModel(List<Veiculo> veiculos) {
        return veiculos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
