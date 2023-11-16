package com.invest.me.money.domain.represetation;

import com.invest.me.money.domain.model.TiposDespesas;
import lombok.Data;
import java.util.List;

@Data
public class DespesaModel {

    private Long codigo;

    private String categoria;

    private List<TiposDespesas> tipos;

}
