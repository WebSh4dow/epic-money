package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;

import java.util.List;

public interface TipoReceitaRepository {
    List<TiposReceitas> listar();
    void remover(TiposReceitas tiposReceitas);
    TiposReceitas incluir(TiposReceitas tiposReceitas);

    TiposReceitas porCodigo(Long codigo);
}
