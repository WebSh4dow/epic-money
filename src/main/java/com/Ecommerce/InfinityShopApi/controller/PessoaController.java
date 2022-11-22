package com.Ecommerce.InfinityShopApi.controller;

import com.Ecommerce.InfinityShopApi.model.Pessoa;
import com.Ecommerce.InfinityShopApi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;
    @GetMapping
    public ResponseEntity <?>listar(){
        return pessoaService.list();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findBy(@PathVariable Long id){
        return pessoaService.findBy(id);
    }
    @PostMapping
    public ResponseEntity <Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
         return pessoaService.create(pessoa,response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id){
        Pessoa update = pessoaService.update(id, pessoa);
        return ResponseEntity.ok(update);
    }
    @PatchMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePessoaBy(@PathVariable Long id,@RequestBody Boolean ativo){
        this.pessoaService.pessoaUpdateByAtivo(id,ativo);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        this.pessoaService.delete(id);
    }

}