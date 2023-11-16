package com.invest.me.money.domain.service;

import com.invest.me.money.domain.exception.EntidadeEmUsoException;
import com.invest.me.money.domain.exception.EntidadeNaoEncontradaException;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.repository.TipoDespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TipoDespesaService {

    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    private static final String MSG_NAO_ENCONTRADA = "Não foi possivel encontrar o tipo de despesa acessado pelo recurso atual: ";

    private static final String MSG_RECEITA_EM_USO = "Não foi possivel excluir o tipo despesa atual pois existe um ou mais tipos de despesa vinculados";

    public List<TiposDespesas> listar() {
        return tipoDespesaRepository.findAll();
    }

    public TiposDespesas porCodigo(Long codigo) {
        return tipoDespesaRepository.findByCodigo(codigo);
    }

    @Transactional
    public void remover(Long codigo){
        try {
            tipoDespesaRepository.deleteById(codigo);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(MSG_RECEITA_EM_USO);

        }catch (EmptyResultDataAccessException | NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(MSG_NAO_ENCONTRADA);
        }
    }

    @Transactional
    public TiposDespesas adicionar(TiposDespesas tiposDespesas) {
        return tipoDespesaRepository.save(tiposDespesas);
    }
}
