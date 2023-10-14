package com.glaubermatos.transito.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.glaubermatos.transito.domain.service.ApreensaoVeiculoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{veiculoId}/apreensao")
public class VeiculoApreensaoController {

    private final ApreensaoVeiculoService apreensaoVeiculoService;
    
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId) {
        apreensaoVeiculoService.apreender(veiculoId);
    }

    @DeleteMapping    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeApreensao(@PathVariable Long veiculoId) {
        apreensaoVeiculoService.removerApreensao(veiculoId);
    }
}
