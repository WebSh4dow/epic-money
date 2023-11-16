package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.DespesaAssembler;
import com.invest.me.money.domain.exception.EntidadeNaoEncontradaException;
import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.represetation.DespesaModel;
import com.invest.me.money.domain.service.DespesaService;
import com.invest.me.money.domain.service.TipoDespesaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/despesas-categorias/tipo-despesa")
public class TiposDespesasController {

    @Autowired
    private TipoDespesaService tipoDespesaService;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaAssembler despesaAssembler;

    private static final String MSG_DESPESA_EM_USO = "Não foi possivel excluir o tipo despesa atual pois existe um ou mais despesas vinculadas a esse tipo.";

    @GetMapping("/listar-por/{tipoDespesaCodigo}")
    public ResponseEntity<List<DespesaModel>> listarPor(@PathVariable Long tipoDespesaCodigo) {
        Despesas despesas = despesaService.porCodigo(tipoDespesaCodigo);

        if (despesas != null) {
            return ResponseEntity.ok().body(despesaService.listar()
                    .stream().map(despesaAssembler::toModel).collect(Collectors.toList()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<?> listar() {
        List<DespesaModel> despesasModelList= despesaService.listar().stream().map(despesaAssembler::toModel).toList();

        if (despesasModelList.isEmpty()){
            Set<String> responseEntityMessage = new HashSet<>();
            responseEntityMessage.add("Atualmente não exite nenhuma despesa cadastrada no sistema");

            return ResponseEntity.badRequest().body(responseEntityMessage);
        }

        return ResponseEntity.ok().body(despesasModelList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody TiposDespesas tiposDespesas) {
        try {
            tiposDespesas = tipoDespesaService.adicionar(tiposDespesas);
            return ResponseEntity.status(HttpStatus.CREATED).body(tiposDespesas);

        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{tipoDespesaCodigo}")
    public ResponseEntity<?> atualizar(@PathVariable Long tipoDespesaCodigo, @RequestBody TiposDespesas tiposDespesas) {
        try {
            TiposDespesas tiposDespesaAtual = tipoDespesaService.porCodigo(tipoDespesaCodigo);

            if (tiposDespesaAtual != null) {
                BeanUtils.copyProperties(tiposDespesas, tiposDespesaAtual, "codigo", "tipos");
                tiposDespesaAtual = tipoDespesaService.adicionar(tiposDespesaAtual);

                return ResponseEntity.ok().body(tiposDespesaAtual);
            }

            return ResponseEntity.notFound().build();

        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{tipoDespesaCodigo}")
    public ResponseEntity<?> remover(@PathVariable Long tipoDespesaCodigo) {
        try {
            TiposDespesas tiposDespesas = tipoDespesaService.porCodigo(tipoDespesaCodigo);

            if (tiposDespesas == null) {
                return ResponseEntity.notFound().build();
            }

            tipoDespesaService.remover(tipoDespesaCodigo);

            Set<String> responseEntityMessage = new HashSet<>();
            responseEntityMessage.add("Despesa Atual foi removida com sucesso!!!!");

            return ResponseEntity.ok().body(responseEntityMessage);

        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.badRequest().body(MSG_DESPESA_EM_USO);
        }
    }

    @DeleteMapping("/desassociar/{despesaCodigo}/{tipoDespesaCodigo}")
    public ResponseEntity<?> desassociar(@PathVariable Long despesaCodigo, @PathVariable Long tipoDespesaCodigo){

        try {
            Despesas despesaAtual = despesaService.porCodigo(despesaCodigo);
            TiposDespesas tiposDespesasAtual = tipoDespesaService.porCodigo(tipoDespesaCodigo);

            if (despesaAtual == null || tiposDespesasAtual == null) {
                return ResponseEntity.notFound().build();
            }

            despesaService.dessasociarTiposDespesas(despesaCodigo, tipoDespesaCodigo);

            Set<String> responseEntityMessage = new HashSet<>();
            responseEntityMessage.add("A dessasociação de tipos de despesas foi realizada com sucesso!!!!");

            return ResponseEntity.ok().body(responseEntityMessage);

        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/associar/{despesaCodigo}/{tipoDespesaCodigo}")
    public ResponseEntity<?> associar(@PathVariable Long despesaCodigo, @PathVariable Long tipoDespesaCodigo) {

        Despesas despesaAtual = despesaService.porCodigo(despesaCodigo);
        TiposDespesas tiposDespesasAtual = tipoDespesaService.porCodigo(tipoDespesaCodigo);

        if (despesaAtual == null || tiposDespesasAtual == null) {
            ResponseEntity.notFound().build();
        }

        despesaService.associarTiposDespesas(despesaCodigo, tipoDespesaCodigo);

        Set<String> responseEntityMessage = new HashSet<>();
        responseEntityMessage.add("A associação de tipos de despesas foi realizada com sucesso!!!!");

        return ResponseEntity.ok().body(responseEntityMessage);
    }
}