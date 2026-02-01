package com.example.desafio_nexdom.domain.entity;

import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private Integer quantidadeMovimentada;

    private Double valorVenda;

    private LocalDateTime dataVenda;

    public MovimentoEstoque() {
        this.dataVenda = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Integer getQuantidadeMovimentada() {
        return quantidadeMovimentada;
    }

    public void setQuantidadeMovimentada(Integer quantidade) {
        this.quantidadeMovimentada = quantidade;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }
}