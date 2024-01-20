package com.invest.me.money.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotNull
    private String descricao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "despesas_tipos",
            joinColumns = @JoinColumn(name = "despesas_codigo"),
            inverseJoinColumns = @JoinColumn(name = "tipos_despesas_codigo"))
    private List<TiposDespesas> tipos = new ArrayList<>();

    public boolean removerReceitas(TiposDespesas tiposDespesas) {
        return getTipos().remove(tiposDespesas);
    }

    public boolean adicionarReceitas(TiposDespesas tiposDespesas) {
        return getTipos().add(tiposDespesas);
    }

}
