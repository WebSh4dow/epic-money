package com.invest.me.money.domain.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotNull
    private String categoria;

    @NotNull
    private String[] subcategoria;
}
