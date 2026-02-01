package com.example.desafio_nexdom.dto;

public class LucroProdutoDTO {

    private Long produtoId;
    private String descricao;
    private Integer quantidadeTotalSaida;
    private Double valorTotalVendido;
    private Double lucroTotal;

    public LucroProdutoDTO(
            Long produtoId,
            String descricao,
            Integer quantidadeTotalSaida,
            Double valorTotalVendido,
            Double lucroTotal
    ) {
        this.produtoId = produtoId;
        this.descricao = descricao;
        this.quantidadeTotalSaida = quantidadeTotalSaida;
        this.valorTotalVendido = valorTotalVendido;
        this.lucroTotal = lucroTotal;
    }
    public Long getProdutoId() {
        return produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidadeTotalSaida() {
        return quantidadeTotalSaida;
    }

    public Double getLucroTotal() {
        return lucroTotal;
    }

    public Double getValorTotalVendido() {
        return valorTotalVendido;
    }

    public void setValorTotalVendido(Double valorTotalVendido) {
        this.valorTotalVendido = valorTotalVendido;
    }
}
