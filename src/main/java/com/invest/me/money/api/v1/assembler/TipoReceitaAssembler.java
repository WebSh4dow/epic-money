package com.invest.me.money.api.v1.assembler;

import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.represetation.TipoReceitaModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class TipoReceitaAssembler {
    public TipoReceitaModel toModel(TiposReceitas tipoReceitas) {
        TipoReceitaModel tiposReceitas = new TipoReceitaModel();

        tiposReceitas.setTag(tipoReceitas.getTag());
        tiposReceitas.setCodigo(tipoReceitas.getCodigo());
        tiposReceitas.setCategorias(tipoReceitas.getCategorias());

        return tiposReceitas;
    }

    public List<TipoReceitaModel> toCollectionModel(List<TiposReceitas> tipoReceitas){
        return tipoReceitas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
