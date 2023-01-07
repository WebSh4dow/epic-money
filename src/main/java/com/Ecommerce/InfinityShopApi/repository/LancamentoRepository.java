package com.Ecommerce.InfinityShopApi.repository;

import com.Ecommerce.InfinityShopApi.model.Lancamento;
import com.Ecommerce.InfinityShopApi.repository.LancamentoQuery.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
