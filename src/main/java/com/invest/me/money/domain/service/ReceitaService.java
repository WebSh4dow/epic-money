package com.invest.me.money.domain.service;

import com.invest.me.money.domain.represetation.ReceitasModel;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.ReceitasRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ReceitaService {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ModelMapper modelMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(ReceitaService.class);


    public List<Receitas> listar() {
        return receitasRepository.findAll();
    }


    @Transactional
    public void remover(Receitas receitas) {
        if (receitas.getCodigo() != null){
            receitasRepository.delete(receitas);
        } else {
            LOGGER.error("[LOGGER PARA O REMOVER]: " + "Não foi possivel remover a receita atual pois ela não existe ou ja foi removida......");
        }
    }


    @Transactional
    public Receitas incluir(Receitas receitas) {
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
