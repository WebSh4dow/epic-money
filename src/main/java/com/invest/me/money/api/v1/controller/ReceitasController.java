package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.ReceitaAssembler;
import com.invest.me.money.domain.exception.TipoReceitaException;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.represetation.ReceitasModel;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.service.ReceitaService;
import com.invest.me.money.domain.service.TipoReceitaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/receitas")
public class ReceitasController {
    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private TipoReceitaService tiposReceitasService;

    @Autowired
    private ReceitaAssembler receitaAssembler;

    private static final String MSG_TIPO_RECEITA_EM_USO = "Tipo de receita está em uso, primeiro deve desvincular o tipo de receitar para poder remover a receita";

    @GetMapping("/listar")
    public List<ReceitasModel> listar(){
        return receitaService.listar()
                .stream()
                .map(receitaAssembler::toModel)
                .collect(Collectors.toList());
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
    @DeleteMapping("/remover/{codigo}")
    public void remover(Receitas receitas, @PathVariable Long codigo){
        Receitas pesquisarPorCodigoReceita = receitaService.porCodigo(codigo);

        if (pesquisarPorCodigoReceita != null){
            for (TiposReceitas tp: pesquisarPorCodigoReceita.getTipos() ){
                TiposReceitas buscarPor = tiposReceitasService.porCodigo(tp.getCodigo());

                if (Objects.equals(buscarPor.getCodigo(), tp.getCodigo())){
                    throw new TipoReceitaException(MSG_TIPO_RECEITA_EM_USO);
                }
                receitaService.remover(receitas);
            }
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
