package com.example.desafio_nexdom.dto;

import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoProduto;

public class ProdutoResumoDTO {

    private Long produtoId;
    private String descricao;
    private TipoProduto tipoProduto;
    private Integer quantidadeDisponivel;
    private Integer quantidadeSaida;

    public ProdutoResumoDTO(
            Long produtoId,
            String descricao,
            TipoProduto tipoProduto,
            Integer quantidadeDisponivel,
            Integer quantidadeSaida
    ) {
        this.produtoId = produtoId;
        this.descricao = descricao;
        this.tipoProduto = tipoProduto;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeSaida = quantidadeSaida;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public Integer getQuantidadeSaida() {
        return quantidadeSaida;
    }
}
