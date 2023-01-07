package com.Ecommerce.InfinityShopApi.repository.Filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
public class LancamentoFilter {
    private String descricao;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoDe;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVEncimentoAte;
}
