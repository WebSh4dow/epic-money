package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.repository.query.DespesaQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DespesasRepository extends JpaRepository<Despesas,Long>, DespesaQuery {
    Despesas findByCodigo(Long codigo);
    @Query(value = QUERY_DESPESA_POR_TIPO_DESPESA,nativeQuery = true)
    List<Despesas> despesasPorTipo(Long codigoTipoDespesa);
}
