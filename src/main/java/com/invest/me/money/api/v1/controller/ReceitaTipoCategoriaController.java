package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.ReceitaAssembler;
import com.invest.me.money.domain.represetation.ReceitasModel;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.service.ReceitaService;
import com.invest.me.money.domain.service.TipoReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas-categorias/tipo-receita")
public class ReceitaTipoCategoriaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ReceitaAssembler receitaAssembler;

    @GetMapping("/listar/{tipoReceitaCodigo}")
    public ResponseEntity <List<ReceitasModel>> listar(@PathVariable Long tipoReceitaCodigo){
        Receitas receitas = receitaService.porCodigo(tipoReceitaCodigo);
        if (receitas != null){
            return ResponseEntity.ok().body(receitaService.listar()
                    .stream().map(receitaAssembler::toModel).collect(Collectors.toList()));
        }
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/desassociar/{receitaCodigo}/{tipoReceitaCodigo}")
    public void desassociar(@PathVariable Long receitaCodigo, @PathVariable Long tipoReceitaCodigo) {
        receitaService.desassociarTiposReceitas(receitaCodigo, tipoReceitaCodigo);
    }

    @PutMapping("/associar/{receitaCodigo}/{tipoReceitaCodigo}")
    public void associar(@PathVariable Long receitaCodigo, @PathVariable Long tipoReceitaCodigo) {
        receitaService.associarTiposReceitas(receitaCodigo, tipoReceitaCodigo);
    }

}
