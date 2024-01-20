package com.invest.me.money.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TiposDespesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    private String tag;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tipos_despesas_categorias",
            joinColumns = @JoinColumn(name = "tipo_despesas_codigo"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categorias> categorias;

}
