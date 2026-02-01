package com.example.desafio_nexdom.domain.entity;

import com.example.desafio_nexdom.domain.enums.TipoProduto;
import jakarta.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoProduto tipoProduto;

    private Double valorFornecedor;

    private Integer quantidadeEstoque;

    public Produto() {};

    public Double getValorFornecedor() {
        return valorFornecedor;
    }

    public void setValorFornecedor(Double valor) {
        this.valorFornecedor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoproduto) {
        this.tipoProduto = tipoproduto;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidade) {
        this.quantidadeEstoque = quantidade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
