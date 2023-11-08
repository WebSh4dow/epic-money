package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.model.Despesas;
import java.util.List;

public interface DespesasRepository {
    List<Despesas> listar();
    Despesas incluir();
    Despesas porCodigo(Long codigo);
    void remover(Despesas despesas);
}
