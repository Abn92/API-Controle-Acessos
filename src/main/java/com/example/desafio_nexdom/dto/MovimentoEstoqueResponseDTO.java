package com.example.desafio_nexdom.dto;

import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de retorno da movimentação de estoque")
public class MovimentoEstoqueResponseDTO {

    private Long id;

    @Schema(description = "ID do produto", example = "1")
    private Long produtoId;

    @Schema(description = "Descrição do produto", example = "Notebook Dell")
    private String produtoDescricao;

    private TipoMovimentacao tipoMovimentacao;

    private Integer quantidadeMovimentada;

    private Double valorVenda;

    private LocalDateTime dataVenda;

    public MovimentoEstoqueResponseDTO(
            Long id,
            Long produtoId,
            String produtoDescricao,
            TipoMovimentacao tipoMovimentacao,
            Integer quantidadeMovimentada,
            Double valorVenda,
            LocalDateTime dataVenda
    ) {
        this.id = id;
        this.produtoId = produtoId;
        this.produtoDescricao = produtoDescricao;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidadeMovimentada = quantidadeMovimentada;
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
    }

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public Integer getQuantidadeMovimentada() {
        return quantidadeMovimentada;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }
}
