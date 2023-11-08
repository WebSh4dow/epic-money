package com.invest.me.money.domain.service;

import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.repository.TipoReceitaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TipoReceitaService implements TipoReceitaRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<TiposReceitas> listar() {
        return null;
    }

    @Override
    public void remover(TiposReceitas tiposReceitas) {

    }

    @Override
    public TiposReceitas incluir(TiposReceitas tiposReceitas) {
        return null;
    }

    @Override
    public TiposReceitas porCodigo(Long codigo) {
        return entityManager.find(TiposReceitas.class,codigo);
    }
}
