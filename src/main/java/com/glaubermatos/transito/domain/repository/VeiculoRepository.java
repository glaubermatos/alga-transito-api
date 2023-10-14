package com.glaubermatos.transito.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glaubermatos.transito.domain.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    Optional<Veiculo> findByPlaca(String placa);
}
