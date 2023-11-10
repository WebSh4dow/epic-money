package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.model.TiposReceitas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoReceitaRepository extends JpaRepository<TiposReceitas,Long> {

    TiposReceitas findByCodigo(Long codigo);
}
