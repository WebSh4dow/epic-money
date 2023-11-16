package com.invest.me.money.domain.service;

import com.invest.me.money.domain.exception.EntidadeEmUsoException;
import com.invest.me.money.domain.exception.EntidadeNaoEncontradaException;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.ReceitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import java.util.*;

@Component
public class ReceitaService {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ModelMapper modelMapper;

    private static final String MSG_NAO_ENCONTRADA = "Não foi possivel encontrar a receita acessado pelo recurso atual: ";

    private static final String MSG_RECEITA_EM_USO = "Não foi possivel excluir a receita atual pois existe um ou mais tipos de receitas vinculados";

    public List<Receitas> listar() {
        return receitasRepository.findAll();
    }

    @Transactional
    public void remover(Receitas receitas) {
        try {
            receitasRepository.delete(receitas);
        }catch (EmptyResultDataAccessException | NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(MSG_NAO_ENCONTRADA);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(MSG_RECEITA_EM_USO);
        }
    }

    @Transactional
    public Receitas incluir(Receitas receitas) {

        receitas.getTipos().forEach(tiposReceitas -> {
            Long tiposReceitasCodigo = tiposReceitas.getCodigo();
            TiposReceitas tipoReceitapesquisada = tipoReceitaService.porCodigo(tiposReceitasCodigo);

            if (tipoReceitapesquisada == null){
                throw new EntidadeNaoEncontradaException("Não existe um cadastro de tipo de receitas com codigo: "+ tiposReceitasCodigo);
            }

            Set<TiposReceitas> adicionarTipos = new HashSet<>();
            adicionarTipos.add(tipoReceitapesquisada);
            
            receitas.setTipos(adicionarTipos);
        });

        return receitasRepository.save(receitas);
    }

    public Receitas porCodigo(Long codigo) {
        return receitasRepository.findById(codigo).get();
    }

    @Transactional
    public void desassociarTiposReceitas(Long codigoReceita, Long codigoTipoReceita) {
        Receitas receitas = porCodigo(codigoReceita);
        TiposReceitas tiposReceitas = tipoReceitaService.porCodigo(codigoTipoReceita);
        receitas.removerReceitas(tiposReceitas);

    }

    @Transactional
    public void associarTiposReceitas(Long receitaCodigo, Long tipoReceitaCodigo) {
        Receitas receitas = porCodigo(receitaCodigo);
        TiposReceitas tiposReceitas = tipoReceitaService.porCodigo(tipoReceitaCodigo);
        receitas.adicionarReceitas(tiposReceitas);
    }

}