package com.Ecommerce.InfinityShopApi.service;

import com.Ecommerce.InfinityShopApi.Event.RecursoCriadoEvent;
import com.Ecommerce.InfinityShopApi.model.Categoria;
import com.Ecommerce.InfinityShopApi.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    public ResponseEntity <Categoria> create(Categoria categoria, HttpServletResponse response){
        Categoria save = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this,response, categoria.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    public Categoria updateProducts(Long id, Categoria categoria){
        Categoria updateProduct = categoriaRepository.findById(id).get();
        if (updateProduct == null){
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(categoria,updateProduct,"id");
        return categoriaRepository.save(updateProduct);
    }

}

