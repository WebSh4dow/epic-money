package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.model.TiposDespesas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDespesaRepository extends JpaRepository<TiposDespesas,Long> {
    TiposDespesas findByCodigo(Long codigo);
}
