package com.invest.me.money.api.v1.controller;

import com.invest.me.money.api.v1.assembler.DespesaAssembler;
import com.invest.me.money.domain.model.Despesas;
import com.invest.me.money.domain.model.TiposDespesas;
import com.invest.me.money.domain.represetation.DespesaModel;
import com.invest.me.money.domain.service.DespesaService;
import com.invest.me.money.domain.service.TipoDespesaService;
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
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private TipoDespesaService tipoDespesaService;

    @Autowired
    private DespesaAssembler despesaAssembler;


    private static final String MSG_TIPO_DESPESA_EM_USO = "Tipo de despesa está em uso, primeiro deve desvincular o tipo de despesa de código: ";


    @GetMapping("/listar")
    public List<DespesaModel> listar() {
        return despesaService.listar()
                .stream()
                .map(despesaAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/editar/{codigo}")
    public ResponseEntity<Despesas> editar(@RequestBody Despesas despesas, @PathVariable Long codigo) {
        Despesas despesaAtual = despesaService.porCodigo(codigo);

        if (despesaAtual != null) {
            BeanUtils.copyProperties(despesas, despesaAtual, "codigo", "tipos");
            despesaAtual = despesaService.adicionar(despesaAtual);
            return ResponseEntity.ok().body(despesaAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<?> remover(@PathVariable Long codigo) {
        try {
            Despesas despesaAtual = despesaService.porCodigo(codigo);

            for (TiposDespesas tiposDespesas : despesaAtual.getTipos()) {
                TiposDespesas buscarPor = tipoDespesaService.porCodigo(tiposDespesas.getCodigo());

                if (Objects.equals(buscarPor.getCodigo(), tiposDespesas.getCodigo())) {
                    return ResponseEntity.badRequest()
                            .body(MSG_TIPO_DESPESA_EM_USO + tiposDespesas.getCodigo());
                }
            }

            despesaService.remover(codigo);
            return ResponseEntity.noContent().build();

        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/incluir")
    public ResponseEntity<Despesas> incluir(@RequestBody Despesas despesas) {
        return new ResponseEntity<>(despesaService.adicionar(despesas), HttpStatus.CREATED);
    }

    @GetMapping("/pesquisar-por/{codigo}")
    public ResponseEntity<Despesas> pesquisarPor(@PathVariable Long codigo) {
        Despesas despesasAtuais = despesaService.porCodigo(codigo);

        if (despesasAtuais != null) {
            return ResponseEntity.ok().body(despesasAtuais);
        }
        return ResponseEntity.notFound().build();
    }
}
