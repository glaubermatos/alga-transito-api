package com.glaubermatos.transito.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.glaubermatos.transito.api.assembler.VeiculoModelAssembler;
import com.glaubermatos.transito.api.assembler.VeiculoModelDisassembler;
import com.glaubermatos.transito.api.model.VeiculoModel;
import com.glaubermatos.transito.api.model.input.VeiculoInputModel;
import com.glaubermatos.transito.domain.repository.VeiculoRepository;
import com.glaubermatos.transito.domain.service.RegistroVeiculoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    
    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService cadastroVeiculoService;
    private final VeiculoModelAssembler veiculoModelAssembler;
    private final VeiculoModelDisassembler veiculoModelDisassembler;
 
    @GetMapping 
    public List<VeiculoModel> listar() {
        return veiculoModelAssembler.toCollectionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculoModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel criar(@Valid @RequestBody VeiculoInputModel veiculoInput) {
        return veiculoModelAssembler
                .toModel(cadastroVeiculoService
                    .cadastrar(veiculoModelDisassembler
                        .toEntityModel(veiculoInput)));
    }
}
