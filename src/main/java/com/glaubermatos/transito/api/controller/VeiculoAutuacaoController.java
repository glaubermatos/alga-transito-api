package com.glaubermatos.transito.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.glaubermatos.transito.api.assembler.AutuacaoModelAssembler;
import com.glaubermatos.transito.api.assembler.AutuacaoModelDisassembler;
import com.glaubermatos.transito.api.model.AutuacaoModel;
import com.glaubermatos.transito.api.model.input.AutuacaoInput;
import com.glaubermatos.transito.domain.service.RegistroAutuacaoService;
import com.glaubermatos.transito.domain.service.RegistroVeiculoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class VeiculoAutuacaoController {

    private final AutuacaoModelAssembler autuacaoModelAssembler;
    private final AutuacaoModelDisassembler autuacaoModelDisassembler;
    private final RegistroAutuacaoService registroAutuacaoService;
    private final RegistroVeiculoService registroVeiculoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel autuar(@PathVariable Long veiculoId, @Valid @RequestBody AutuacaoInput autuacaoInput) {
        return autuacaoModelAssembler
                .toModel(registroAutuacaoService
                        .registrar(veiculoId, autuacaoModelDisassembler.toEntity(autuacaoInput)));
    }

    @GetMapping
    public List<AutuacaoModel> listar(@PathVariable Long veiculoId) {
        var veiculo = registroVeiculoService.buscarVeiculoByIdOrElseThrow(veiculoId);

        return autuacaoModelAssembler.toCollectionModel(veiculo.getAutuacoes());
    }
}
