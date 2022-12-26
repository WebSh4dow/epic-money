package com.Ecommerce.InfinityShopApi.controller;

import com.Ecommerce.InfinityShopApi.model.Lancamento;
import com.Ecommerce.InfinityShopApi.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    public ResponseEntity<?> list(){
        return lancamentoService.list();
    }
    @PostMapping
    public ResponseEntity<Lancamento>save(@RequestBody Lancamento lancamento, HttpServletResponse response){
        return lancamentoService.create(lancamento,response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento>findBy(@PathVariable Long id){
        return lancamentoService.findBy(id);
    }

    @PutMapping("/{id}")
    public Lancamento update(@PathVariable Long id, @RequestBody Lancamento lancamento){
        return lancamentoService.update(id, lancamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        this.lancamentoService.remove(id);
    }
}
