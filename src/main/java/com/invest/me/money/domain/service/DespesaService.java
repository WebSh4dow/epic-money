package com.invest.me.money.domain.service;

import com.invest.me.money.domain.exception.EntidadeEmUsoException;
import com.invest.me.money.domain.exception.EntidadeNaoEncontradaException;
import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.repository.DespesasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DespesaService {

    @Autowired
    private TipoDespesaService tipoDespesaService;

    @Autowired
    private DespesasRepository despesasRepository;

    private static final String MSG_NAO_ENCONTRADA = "Não foi possivel encontrar a despesa acessado pelo recurso atual: ";

    private static final String MSG_RECEITA_EM_USO = "Não foi possivel excluir a despesa atual pois existe um ou mais tipos de despesa vinculados";

    public List<Despesas> listar() {
        return despesasRepository.findAll();
    }

    public Despesas porCodigo(Long codigo) {
        return despesasRepository.findByCodigo(codigo);
    }

    @Transactional
    public Despesas adicionar(Despesas despesas) {

        despesas.getTipos().forEach(tiposDespesas -> {
            Long tipoDespesaCodigo = tiposDespesas.getCodigo();
            TiposDespesas tiposDespesasPesquisadaAtual = tipoDespesaService.porCodigo(tipoDespesaCodigo);

            if (tiposDespesasPesquisadaAtual == null) {
                throw new EntidadeNaoEncontradaException("Não existe um cadastro de tipo de despesa com codigo: "+ tipoDespesaCodigo);
            }

            Set<TiposDespesas> adicionarTipos = new HashSet<>();
            adicionarTipos.add(tiposDespesasPesquisadaAtual);

            despesas.setTipos(adicionarTipos);
        });
        return despesasRepository.save(despesas);
    }

    @Transactional
    public void remover(Long codigo) {
        try {
            Despesas despesas = despesasRepository.findByCodigo(codigo);
            if (despesas == null) {
                throw new EntidadeNaoEncontradaException("Não existe um cadastro de despesas com o código: " + codigo);
            }
            despesasRepository.deleteById(codigo);
        }catch (EmptyResultDataAccessException | NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException(MSG_NAO_ENCONTRADA);

        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_RECEITA_EM_USO);
        }
    }

    @Transactional
    public void dessasociarTiposDespesas(Long codigoDespesa,Long codigoTipoDespesa) {
        try {
            Despesas despesas = porCodigo(codigoDespesa);
            TiposDespesas tiposDespesas = tipoDespesaService.porCodigo(codigoTipoDespesa);

            despesas.removerReceitas(tiposDespesas);

        } catch (EntidadeNaoEncontradaException | EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(MSG_NAO_ENCONTRADA);
        }
    }

    @Transactional
    public void associarTiposDespesas(Long codigoDespesa, Long codigoTipoDespesa) {
        try {
            Despesas despesas = porCodigo(codigoDespesa);
            TiposDespesas tiposDespesas = tipoDespesaService.porCodigo(codigoTipoDespesa);

            if (despesas == null) {
                throw new EntidadeNaoEncontradaException("Não foi possivel encontrar a despesa atual com código: " + codigoDespesa);
            }
            despesas.adicionarReceitas(tiposDespesas);

        } catch (EntidadeNaoEncontradaException | EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(MSG_NAO_ENCONTRADA + codigoDespesa);
        }
    }
}
