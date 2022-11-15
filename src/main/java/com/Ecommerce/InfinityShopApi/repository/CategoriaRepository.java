package com.Ecommerce.InfinityShopApi.repository;

import com.Ecommerce.InfinityShopApi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
