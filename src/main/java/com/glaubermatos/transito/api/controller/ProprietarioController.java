package com.glaubermatos.transito.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glaubermatos.transito.domain.model.Proprietario;
import com.glaubermatos.transito.domain.repository.ProprietarioRepository;
import com.glaubermatos.transito.domain.service.RegistroProprietarioService;

import jakarta.validation.Valid;
// import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioRepository proprietarioRepository;
    private final RegistroProprietarioService registroProprietarioService;
    
    @GetMapping
    public List<Proprietario> listar() {
        // TypedQuery<Proprietario> query = entityManager.createQuery("from Proprietario", Proprietario.class);

        // return query.getResultList();

        return proprietarioRepository.findAll();
    }

    @GetMapping("/por-nome")
    public List<Proprietario> findByName(@RequestParam String nome) {
        return proprietarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) {
        return proprietarioRepository.findById(proprietarioId)
                .map(ResponseEntity::ok) //é o mesmo que: (proprietario) -> ResponseEntity.ok(proprierio)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Proprietario> criar(@Valid @RequestBody Proprietario proprietario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroProprietarioService.registrar(proprietario));
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(@Valid @PathVariable Long proprietarioId,
                                                  @RequestBody Proprietario proprietario) {
        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.registrar(proprietario);

        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> remover(@PathVariable Long proprietarioId) {
        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        registroProprietarioService.excluir(proprietarioId);

        return ResponseEntity.noContent().build();
    }
}
