package com.example.desafio_nexdom.dto;

import com.example.desafio_nexdom.domain.enums.TipoProduto;

public class ProdutoRequest {

    private String descricao;
    private TipoProduto tipoProduto;
    private Double valorFornecedor;
    private Integer quantidadeEstoque;

    public String getDescricao() {
        return descricao;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public Double getValorFornecedor() {
        return valorFornecedor;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}
