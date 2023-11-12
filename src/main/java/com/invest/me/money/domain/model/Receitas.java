package com.invest.me.money.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Receitas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotNull
    private String descricao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "receitas_tipos",
            joinColumns = @JoinColumn(name = "receitas_codigo"),
            inverseJoinColumns = @JoinColumn(name = "tipos_receitas_codigo"))
    private Set<TiposReceitas> tipos = new HashSet<>();

    public boolean removerReceitas(TiposReceitas tiposReceitas) {
        return getTipos().remove(tiposReceitas);
    }

    public boolean adicionarReceitas(TiposReceitas tiposReceitas) {
        return getTipos().add(tiposReceitas);
    }


}
