package com.invest.me.money.domain.repository;


import com.invest.me.money.domain.model.Receitas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasRepository extends JpaRepository<Receitas,Long> {
}
