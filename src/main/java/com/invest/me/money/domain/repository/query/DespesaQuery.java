package com.invest.me.money.domain.repository.query;

public interface DespesaQuery {
    public static String QUERY_DESPESA_POR_TIPO_DESPESA =
            "SELECT " +
                    "c.descricao as descricao, " +
                    "c.codigo as codigo, " +
                    "td.codigo as tipo_despesa_codigo, " +
                    "td.tag as tag_tipo_despesa, " +
                    "td.categorias as categorias_tipo_despesa " +
                    "FROM despesas as c " +
                    "INNER JOIN tipos_despesas as td ON c.codigo = td.codigo " +
                    "WHERE td.codigo = ?1";
}
