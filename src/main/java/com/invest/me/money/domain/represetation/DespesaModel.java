package com.invest.me.money.domain.represetation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.invest.me.money.domain.model.TiposDespesas;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("despesas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DespesaModel extends RepresentationModel<DespesaModel> {

    private Long codigo;

    private String categoria;

    private List<TiposDespesas> tipos;

}
