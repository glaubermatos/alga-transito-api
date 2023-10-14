package com.glaubermatos.transito.api.model;

import java.time.OffsetDateTime;

import com.glaubermatos.transito.domain.model.StatusVeiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoModel {
    
    private Long id;
    private ProprietarioResumeModel proprietario;
    private String marca;
    private String modelo;
    private String placa;
    private StatusVeiculo status;
    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataApreensao;
}
