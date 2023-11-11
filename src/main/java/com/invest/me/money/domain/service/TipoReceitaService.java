package com.invest.me.money.domain.service;

import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.TipoReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoReceitaService {

    @Autowired
    private TipoReceitaRepository tipoReceitaRepository;


    public List<TiposReceitas> listar(){
        return tipoReceitaRepository.findAll();
    }

    public TiposReceitas porCodigo(Long codigo) {
        return tipoReceitaRepository.findByCodigo(codigo);
    }

    public TiposReceitas adicionar(TiposReceitas tiposReceitas){
        return tipoReceitaRepository.save(tiposReceitas);
    }

    public void remover(TiposReceitas tiposReceitas){
         tipoReceitaRepository.delete(tiposReceitas);
    }

}
