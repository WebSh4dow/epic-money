package com.invest.me.money.domain.repository;

import com.invest.me.money.domain.dto.ReceitasDTO;
import com.invest.me.money.domain.model.Receitas;
import java.util.List;

public interface ReceitasRepository {
    List<Receitas> listar();

    List<ReceitasDTO> listarDto();
    void remover(Receitas receitas);
    Receitas incluir(Receitas receitas);

    Receitas porCodigo(Long codigo);


}
