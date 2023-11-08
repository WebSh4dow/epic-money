package com.invest.me.money.api.v1.assembler;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.represetation.ReceitasModel;
import com.invest.me.money.domain.model.Receitas;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceitaAssembler {
    public ReceitasModel toModel(Receitas receitas) {
        ReceitasModel receitasModel = new ReceitasModel();
        List<TiposReceitas> tiposReceitas = new ArrayList<>();

        receitasModel.setCategoria(receitas.getCategoria());
        tiposReceitas.addAll(receitas.getTipos());

        receitasModel.setCodigo(receitas.getCodigo());
        receitasModel.setTipos(tiposReceitas);

        return receitasModel;
    }

    public List<ReceitasModel> toCollectionModel(List<Receitas> receitas){
        return receitas.stream()
                .map(rc ->toModel(rc))
                .collect(Collectors.toList());
    }

}

