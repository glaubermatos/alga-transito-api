package com.glaubermatos.transito.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaubermatos.transito.domain.exception.EmailJaCadastradoException;
import com.glaubermatos.transito.domain.exception.EntidadeNaoEncontradaException;
import com.glaubermatos.transito.domain.model.Proprietario;
import com.glaubermatos.transito.domain.repository.ProprietarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {
    
    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Proprietario registrar(Proprietario proprietario) {
        boolean emailJaCadastrado = proprietarioRepository.findByEmailIgnoreCase(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent();

        if (emailJaCadastrado) {
            throw new EmailJaCadastradoException("Já existe um proprietário cadastrado com esse e-mail");
        }

        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long proprietarioId) {
        proprietarioRepository.deleteById(proprietarioId);
    }

    public Proprietario buscarProprietarioByIdOrElseThrow(Long proprietarioId) {
        return proprietarioRepository.findById(proprietarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Proprietário de id %d não existe", proprietarioId)));
    }
}
