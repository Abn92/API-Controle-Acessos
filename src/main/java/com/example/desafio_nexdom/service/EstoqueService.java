package com.example.desafio_nexdom.service;

import com.example.desafio_nexdom.domain.entity.MovimentoEstoque;
import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import com.example.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.example.desafio_nexdom.repository.MovimentoEstoqueRepository;
import com.example.desafio_nexdom.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoRepository;

    public EstoqueService(
            ProdutoRepository produtoRepository,
            MovimentoEstoqueRepository movimentoRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.movimentoRepository = movimentoRepository;
    }

    public List<MovimentoEstoqueResponseDTO> listAll() {
        List<MovimentoEstoque> movimentoEstoqueList = movimentoRepository.findAll();

        return movimentoEstoqueList.stream()
                .map(movimento -> new MovimentoEstoqueResponseDTO(
                        movimento.getId(),
                        movimento.getProduto().getId(),
                        movimento.getProduto().getDescricao(),
                        movimento.getTipoMovimentacao(),
                        movimento.getQuantidadeMovimentada(),
                        movimento.getValorVenda(),
                        movimento.getDataVenda()
                ))
                .toList();
    }

    public void movimentarEstoque(
            Long produtoId,
            TipoMovimentacao tipo,
            Integer quantidade,
            Double valorVenda
    ) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));

        if (tipo == TipoMovimentacao.SAIDA) {
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Quantidade insuficiente em estoque"
                );
            }

            produto.setQuantidadeEstoque(
                    produto.getQuantidadeEstoque() - quantidade
            );
        }

        if (tipo == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(
                    produto.getQuantidadeEstoque() + quantidade
            );
        }

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setProduto(produto);
        movimento.setTipoMovimentacao(tipo);
        movimento.setQuantidadeMovimentada(quantidade);
        movimento.setValorVenda(valorVenda);

        produtoRepository.save(produto);
        movimentoRepository.save(movimento);
    }
}
