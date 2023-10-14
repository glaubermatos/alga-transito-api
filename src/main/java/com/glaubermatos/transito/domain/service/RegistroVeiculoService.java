package com.glaubermatos.transito.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaubermatos.transito.domain.exception.EntidadeNaoEncontradaException;
import com.glaubermatos.transito.domain.exception.NegocioException;
import com.glaubermatos.transito.domain.exception.PlacaJaCadastradaException;
import com.glaubermatos.transito.domain.model.Proprietario;
import com.glaubermatos.transito.domain.model.StatusVeiculo;
import com.glaubermatos.transito.domain.model.Veiculo;
import com.glaubermatos.transito.domain.repository.VeiculoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final RegistroProprietarioService cadastroProprietarioService;

    @Transactional
    public Veiculo cadastrar(Veiculo veiculo) {
        if (veiculo.getId() != null) {
            throw new NegocioException("Veículo a ser cadastrado não deve possuir um id");
        }

        Proprietario proprietario = cadastroProprietarioService
                .buscarProprietarioByIdOrElseThrow(
                        veiculo.getProprietario().getId());

        boolean placaJaCadastrada = veiculoRepository.findByPlaca(veiculo.getPlaca())
                .filter(v -> !v.equals(veiculo))
                .isPresent();

        if (placaJaCadastrada) {
            throw new PlacaJaCadastradaException("Já existe um veiculo cadastrado com essa placa");
        }

        veiculo.setStatus(StatusVeiculo.REGULAR);
        veiculo.setDataCadastro(OffsetDateTime.now());
        veiculo.setProprietario(proprietario);

        System.out.println(veiculo.getDataCadastro());
        
        return veiculoRepository.save(veiculo);
    }

    public Veiculo buscarVeiculoByIdOrElseThrow(Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Veículo de id %d não existe", veiculoId)));
    }
}