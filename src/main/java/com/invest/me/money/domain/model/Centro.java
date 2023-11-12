package com.invest.me.money.domain.model;

import com.invest.me.money.domain.enums.TipoCentro;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Centro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoCentro tipoCentro;
}
