package com.invest.me.money.domain.enums;

import lombok.Getter;

@Getter
public enum TipoCentro {

    CENTRO_CUSTO_PRODUTIVO("Centro de custo Produtivo"),
    CENTRO_CUSTO_NAO_PRODUTIVO("Centro de custo Não Produtivo");

    private final String tipoCentro;

     TipoCentro(String tipoCentro){
         this.tipoCentro = tipoCentro;
     }
}
