package com.example.desafio_nexdom.repository;

import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByTipoProduto(TipoProduto tipoProduto);

    Produto getProdutoById(Long id);
}
