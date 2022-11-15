package com.Ecommerce.InfinityShopApi.controller;

import com.Ecommerce.InfinityShopApi.model.Categoria;
import com.Ecommerce.InfinityShopApi.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        return categoriaService.create(categoria,response);
    }
    @PutMapping("/{id}")
    public Categoria update(@Valid @RequestBody Categoria categoria, @PathVariable Long id){
        return categoriaService.updateProducts(id, categoria);
    }
}
