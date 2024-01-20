package com.invest.me.money.api.v1.assembler;

import com.invest.me.money.api.v1.controller.DespesasController;
import com.invest.me.money.api.v1.controller.TiposDespesasController;
import com.invest.me.money.api.v1.controller.TiposReceitasController;
import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.represetation.DespesaModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DespesaAssembler extends RepresentationModelAssemblerSupport<Despesas, DespesaModel> {

    public DespesaAssembler() {
        super(DespesasController.class, DespesaModel.class);
    }
    @Override
    public DespesaModel toModel(Despesas despesas) {
        DespesaModel despesaModel = new DespesaModel();

        despesaModel.setCategoria(despesas.getDescricao());
        despesaModel.setCodigo(despesas.getCodigo());
        despesaModel.setTipos(new ArrayList<>(despesas.getTipos()));

        despesaModel.add(linkTo(methodOn(DespesasController.class).pesquisarPor(despesas.getCodigo())).withSelfRel().withRel("despesa"));

        for (TiposDespesas tipoDespesa : despesas.getTipos()) {
            despesaModel.add(linkTo(methodOn(TiposDespesasController.class).listarPor(tipoDespesa.getCodigo())).withRel("tipo de despesa"));
        }

        return despesaModel;
    }

    public List<DespesaModel> toCollectionModel(List<Despesas> despesas){
        return despesas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
