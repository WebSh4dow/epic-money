package com.invest.me.money.domain.represetation;


import com.invest.me.money.domain.model.TiposReceitas;
import lombok.Data;
import java.util.List;

@Data
public class ReceitasModel {

    private Long codigo;

    private String categoria;

    private List<TiposReceitas> tipos;

}
