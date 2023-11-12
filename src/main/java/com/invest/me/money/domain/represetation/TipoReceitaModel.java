package com.invest.me.money.domain.represetation;

import lombok.Data;

@Data
public class TipoReceitaModel {

    private Long codigo;

    private String tag;

    private String[] categorias;
}
