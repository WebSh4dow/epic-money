package com.Ecommerce.InfinityShopApi.repository;

import com.Ecommerce.InfinityShopApi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository <Pessoa, Long> {
    Pessoa findClienteByid (Long id);
}
