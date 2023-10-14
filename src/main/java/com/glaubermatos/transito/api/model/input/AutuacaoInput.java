package com.glaubermatos.transito.api.model.input;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutuacaoInput {

	private String descricao;
	private BigDecimal valorMulta;
}