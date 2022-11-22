package com.Ecommerce.InfinityShopApi.service;

import com.Ecommerce.InfinityShopApi.model.Lancamento;
import com.Ecommerce.InfinityShopApi.repository.LancamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LancamentoService {
    @Autowired
    private LancamentoRepository lancamentoRepository;

    public ResponseEntity<?>list(){
        List<Lancamento>lancamentoList = lancamentoRepository.findAll();
        return !lancamentoList.isEmpty() ?ResponseEntity.ok().body
                (lancamentoList):ResponseEntity.notFound().build();
    }

    public ResponseEntity <Lancamento>findBy(Long id){
       Lancamento lancamento = lancamentoRepository.findById(id).get();
       return lancamento!=null ? ResponseEntity.ok().body
               (lancamento):ResponseEntity.notFound().build();
    }

    public ResponseEntity<Lancamento> create(Lancamento lancamento){
        Lancamento salvarLancamento = lancamentoRepository.save(lancamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarLancamento);
    }
    public Lancamento update(Long id, Lancamento lancamento){
       Lancamento updatePessoa= lancamentoRepository.findById(id).get();
       if (updatePessoa == null){
           throw  new EmptyResultDataAccessException(1);
       }
        BeanUtils.copyProperties(lancamento,updatePessoa,"id");
       return lancamentoRepository.save(updatePessoa);
    }
    public void remove(Long id){
        lancamentoRepository.deleteById(id);
    }
}
