package com.invest.me.money.domain.dto;


import com.invest.me.money.domain.model.TiposReceitas;
import lombok.Data;
import java.util.List;

@Data
public class ReceitasDTO {

    private Long codigo;

    private String categoria;

    private List<TiposReceitas> tipos;

}
