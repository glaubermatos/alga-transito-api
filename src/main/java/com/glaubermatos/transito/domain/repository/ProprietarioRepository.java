package com.glaubermatos.transito.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glaubermatos.transito.domain.model.Proprietario;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    
    List<Proprietario> findByNomeContainingIgnoreCase(String nome);
    Optional<Proprietario> findByEmailIgnoreCase(String email);
}
