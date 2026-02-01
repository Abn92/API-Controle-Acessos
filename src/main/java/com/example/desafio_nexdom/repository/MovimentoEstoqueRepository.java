package com.example.desafio_nexdom.repository;

import com.example.desafio_nexdom.domain.entity.MovimentoEstoque;
import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

    List<MovimentoEstoque> findByProduto(Produto produto);

    // Consulta de produtos por tipo, com quantidade de saída e quantidade disponível
    @Query("""
        SELECT COALESCE(SUM(m.quantidadeMovimentada), 0)
        FROM MovimentoEstoque m
        WHERE m.produto.id = :produtoId
        AND m.tipoMovimentacao = :tipo""")
    Integer somarQuantidadePorProdutoETipo(
            @Param("produtoId") Long produtoId,
            @Param("tipo") TipoMovimentacao tipo
    );

    // Consulta de lucro por produto, exibindo a quantidade total de saída,
    // e total do lucro (valor de venda – valor do fornecedor).
    List<MovimentoEstoque> findByProdutoIdAndTipoMovimentacao(
            Long produtoId,
            TipoMovimentacao tipo
    );

    boolean existsByProdutoId(Long id);
}
