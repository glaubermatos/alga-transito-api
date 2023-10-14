package com.glaubermatos.transito.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.glaubermatos.transito.domain.exception.NegocioException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Veiculo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Valid
    // @ConvertGroup(from = Default.class, to = ValidationGroup.ProprietarioId.class)
    // @NotNull
    @ManyToOne
    @JoinColumn(name = "proprietarioId")
    private Proprietario proprietario;

    // @NotBlank
    // @Size(max = 20)
    private String marca;

    // @NotBlank
    // @Size(max = 20)
    private String modelo;

    // @NotBlank
    // @Size(max = 7)
    // @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

    // @JsonProperty(access = Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    // @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataCadastro;

    // @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataApreensao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Autuacao> autuacoes = new ArrayList<Autuacao>();

    public Autuacao adicionaAutuacao(Autuacao autuacao) {
        autuacao.setVeiculo(this);
        autuacao.setDataOcorrencia(OffsetDateTime.now());

        getAutuacoes().add(autuacao);

        return autuacao;
    }

    public void apreender() {
        if (estaApreendido()) {
            throw new NegocioException("Veículo já se encontra apreendido");
        }

        setStatus(StatusVeiculo.APREENDIDO);
        setDataApreensao(OffsetDateTime.now());
    }

    public void removerApreensao() {
        if (naoEstaApreendido()) {
            throw new NegocioException("Veículo não está apreendido");
        }

        setStatus(StatusVeiculo.REGULAR);
        setDataApreensao(null);
    }

    public boolean estaApreendido() {
        return StatusVeiculo.APREENDIDO.equals(getStatus());
    }

    public boolean naoEstaApreendido() {
        return !estaApreendido();
    }
}
