package com.invest.me.money.api.v1.controller;

import com.invest.me.money.domain.dto.ReceitasDTO;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.service.ReceitaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/receitas")
public class ReceitasController {
    @Autowired
    private ReceitaService receitaService;

    @GetMapping("/listar")
    public List<ReceitasDTO> listar(){
        return receitaService.listarDto();
    }

    @PutMapping("/editar/{codigo}")
    public ResponseEntity<Receitas> editar(@RequestBody  Receitas receitas, @PathVariable Long codigo){
           Receitas receitaAtual = receitaService.porCodigo(codigo);

            if (receitaAtual != null){
                BeanUtils.copyProperties(receitas,receitaAtual,"codigo","tipos");
                receitaService.incluir(receitas);
                return ResponseEntity.ok().body(receitaAtual);
            }

            return ResponseEntity.notFound().build();
    }
    @PutMapping("/remover/{codigo}")
    public void remover(Receitas receitas, @PathVariable Long codigo){
        Receitas pesquisarPorCodigoReceita = receitaService.porCodigo(codigo);
        if (pesquisarPorCodigoReceita != null){
            receitaService.remover(receitas);
        }
    }

    @PostMapping("/incluir")
    public ResponseEntity <Receitas> incluir(@RequestBody  Receitas receitas) {
        return new ResponseEntity<Receitas>(receitaService.incluir(receitas), HttpStatus.CREATED);
    }

    @PutMapping("/pesquisar-por/{codigo}")
    public ResponseEntity <Receitas> pesquisarPor(@RequestBody  Receitas receitas, @PathVariable Long codigo){
        Receitas pesquisarPorCodigoReceita = receitaService.porCodigo(codigo);

        if (pesquisarPorCodigoReceita.getCodigo() != null){
            receitaService.incluir(receitas);
            return new ResponseEntity<Receitas>(receitas, HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }



}
