package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.ReceitaAssembler;
import com.invest.me.money.domain.exception.EntidadeNaoEncontradaException;
import com.invest.me.money.domain.exception.EntidadeEmUsoException;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas-categorias/tipo-receita")
public class TiposReceitasController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ReceitaAssembler receitaAssembler;

    @GetMapping("/listar/{tipoReceitaCodigo}")
    public ResponseEntity<List<ReceitasModel>> listarPor(@PathVariable Long tipoReceitaCodigo) {
        Receitas receitas = receitaService.porCodigo(tipoReceitaCodigo);
        if (receitas != null){
            return ResponseEntity.ok().body(receitaService.listar()
                    .stream().map(receitaAssembler::toModel).collect(Collectors.toList()));
        }
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody TiposReceitas tiposReceitas) {
        try {
             tiposReceitas = tipoReceitaService.adicionar(tiposReceitas);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tipoReceitaService.adicionar(tiposReceitas));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{tipoReceitaCodigo}")
    public ResponseEntity<?> atualizar(@RequestBody TiposReceitas tiposReceitas, @PathVariable Long tipoReceitaCodigo) {
        try {
            TiposReceitas tipoReceitaAtual = tipoReceitaService.porCodigo(tipoReceitaCodigo);
            if (tipoReceitaAtual != null) {
                BeanUtils.copyProperties(tiposReceitas, tipoReceitaAtual, "codigo");
                tipoReceitaAtual = tipoReceitaService.adicionar(tipoReceitaAtual);

                return ResponseEntity.ok().body(tipoReceitaAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{tipoReceitaCodigo}")
    public ResponseEntity<?> remover(@PathVariable Long tipoReceitaCodigo) {
        try {
            TiposReceitas tiposReceitas = tipoReceitaService.porCodigo(tipoReceitaCodigo);
            tipoReceitaService.remover(tiposReceitas);

            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
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