package com.invest.me.money.api.v1.assembler;

import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.represetation.DespesaModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class DespesaAssembler {

    public DespesaModel toModel(Despesas despesas) {
        DespesaModel despesaModel = new DespesaModel();
        List<TiposDespesas> tiposDespesas = new ArrayList<>();

        despesaModel.setCategoria(despesas.getDescricao());
        despesaModel.setCodigo(despesas.getCodigo());

        tiposDespesas.addAll(despesas.getTipos());
        despesaModel.setTipos(tiposDespesas);

        return despesaModel;
    }

    public List<DespesaModel> toCollectionModel(List<Despesas> despesas){
        return despesas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
