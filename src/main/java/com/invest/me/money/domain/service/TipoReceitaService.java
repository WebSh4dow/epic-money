package com.invest.me.money.domain.service;

import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.TipoReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoReceitaService {

    @Autowired
    private TipoReceitaRepository tipoReceitaRepository;

    public TiposReceitas porCodigo(Long codigo) {
        return tipoReceitaRepository.findByCodigo(codigo);
    }

}
