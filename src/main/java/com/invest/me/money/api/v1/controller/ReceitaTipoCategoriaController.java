package com.invest.me.money.api.v1.controller;

import com.invest.me.money.domain.dto.ReceitasDTO;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas-categorias/tipo-receita")
public class ReceitaTipoCategoriaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping("/listar/{tipoReceitaCodigo}")
    public ResponseEntity <List<ReceitasDTO>> listar(@PathVariable Long tipoReceitaCodigo){
        Receitas receitas = receitaService.porCodigo(tipoReceitaCodigo);
        if (receitas != null){
            return ResponseEntity.ok().body(receitaService.listarDto());
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
