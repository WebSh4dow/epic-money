package com.invest.me.money.domain.service;


import com.invest.me.money.domain.dto.ReceitasDTO;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.ReceitasRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ReceitaService implements ReceitasRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ModelMapper modelMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(ReceitaService.class);
    @Override
    public List<Receitas> listar(){
        return entityManager.createQuery("from Receitas", Receitas.class).getResultList();

    }

    @Override
    public List<ReceitasDTO> listarDto() {
        TypedQuery<Receitas> query = entityManager.createQuery("SELECT r FROM Receitas r", Receitas.class);
        List<Receitas> receitasList = query.getResultList();

        List<ReceitasDTO> receitasDtoList = receitasList.stream()
                .map(receita -> modelMapper.map(receita, ReceitasDTO.class))
                .collect(Collectors.toList());

        return receitasDtoList;
    }

    @Override
    @Transactional
    public void remover(Receitas receitas) {
        if (receitas.getCodigo() != null){
            entityManager.remove(receitas);
        } else {
            LOGGER.error("[LOGGER PARA O REMOVER]: " + "Não foi possivel remover a receita atual pois ela não existe ou ja foi removida......");
        }
    }

    @Override
    @Transactional
    public Receitas incluir(Receitas receitas) {
        return entityManager.merge(receitas);
    }

    @Override
    public Receitas porCodigo(Long codigo) {
        return entityManager.find(Receitas.class,codigo);
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
        System.out.println(receitas.getCategoria());
        TiposReceitas tiposReceitas = tipoReceitaService.porCodigo(tipoReceitaCodigo);
        System.out.println(tiposReceitas.getDescricao());
        receitas.adicionarReceitas(tiposReceitas);
    }

}
