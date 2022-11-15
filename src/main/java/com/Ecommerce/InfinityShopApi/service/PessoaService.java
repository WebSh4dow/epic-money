package com.Ecommerce.InfinityShopApi.service;

import com.Ecommerce.InfinityShopApi.Event.RecursoCriadoEvent;
import com.Ecommerce.InfinityShopApi.model.Pessoa;
import com.Ecommerce.InfinityShopApi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ApplicationEventPublisher publisher;
    public ResponseEntity<?>list(){
        List <Pessoa> pessoas = pessoaRepository.findAll();
        return !pessoas.isEmpty()?ResponseEntity.ok(pessoas):ResponseEntity.notFound().build();
    }
    public ResponseEntity<Pessoa> findBy(Long id){
        Pessoa clientes = pessoaRepository.findClienteByid(id);
        return clientes!=null ? ResponseEntity.ok(clientes):ResponseEntity.notFound().build();
    }
    public ResponseEntity <Pessoa> create(Pessoa pessoa, HttpServletResponse response){
      Pessoa salvarPessoa = pessoaRepository.save(pessoa);
      publisher.publishEvent(new RecursoCriadoEvent(this,response, salvarPessoa.getId()));
      return ResponseEntity.status(HttpStatus.CREATED).body(salvarPessoa);
    }
    public Pessoa update(Long id, Pessoa pessoa){
        Pessoa Update = pessoaRepository.findClienteByid(id);
        if (Update ==null){
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(pessoa,Update,"id");
        return pessoaRepository.save(Update);
    }
    public void delete(Long id){
        pessoaRepository.deleteById(id);
    }
    public void pessoaUpdateByAtivo(Long id, Boolean ativo){
       Pessoa pessoa = pessoaRepository.findClienteByid(id);
       pessoa.setAtivo(ativo);
       pessoaRepository.save(pessoa);
    }

}