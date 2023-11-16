package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.ReceitaAssembler;
import com.invest.me.money.domain.model.TiposReceitas;
import com.invest.me.money.domain.represetation.ReceitasModel;
import com.invest.me.money.domain.model.Receitas;
import com.invest.me.money.domain.service.ReceitaService;
import com.invest.me.money.domain.service.TipoReceitaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
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

    private static final String MSG_TIPO_RECEITA_EM_USO = "Tipo de receita está em uso, primeiro deve desvincular o tipo de receita de código: ";

    @GetMapping("/listar")
    public List<ReceitasModel> listar() {
        return receitaService.listar()
                .stream()
                .map(receitaAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/editar/{codigo}")
    public ResponseEntity<Receitas> editar(@RequestBody Receitas receitas, @PathVariable Long codigo) {
        Receitas receitaAtual = receitaService.porCodigo(codigo);

        if (receitaAtual != null) {
            BeanUtils.copyProperties(receitas, receitaAtual, "codigo", "tipos");
            receitaAtual = receitaService.incluir(receitaAtual);
            return ResponseEntity.ok().body(receitaAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<?> remover(Receitas receitas, @PathVariable Long codigo) {
        try {
            Receitas receitaAtual = receitaService.porCodigo(codigo);

            for (TiposReceitas tiposReceitas : receitaAtual.getTipos()) {
                TiposReceitas buscarPor = tiposReceitasService.porCodigo(tiposReceitas.getCodigo());

                if (Objects.equals(buscarPor.getCodigo(), tiposReceitas.getCodigo())) {
                    return ResponseEntity.badRequest()
                            .body(MSG_TIPO_RECEITA_EM_USO + tiposReceitas.getCodigo());
                }
            }

            receitaService.remover(receitas);
            return ResponseEntity.noContent().build();

        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/incluir")
    public ResponseEntity<Receitas> incluir(@RequestBody Receitas receitas) {
        return new ResponseEntity<Receitas>(receitaService.incluir(receitas), HttpStatus.CREATED);
    }

    @GetMapping("/pesquisar-por/{codigo}")
    public ResponseEntity<Receitas> pesquisarPor(@PathVariable Long codigo) {
        Receitas receitaAtual = receitaService.porCodigo(codigo);

        if (receitaAtual != null) {
            return ResponseEntity.ok().body(receitaAtual);
        }
        return ResponseEntity.notFound().build();
    }
}
