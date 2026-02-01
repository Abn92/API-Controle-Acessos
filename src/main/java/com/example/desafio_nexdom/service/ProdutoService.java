package com.example.desafio_nexdom.service;

import com.example.desafio_nexdom.domain.entity.MovimentoEstoque;
import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import com.example.desafio_nexdom.domain.enums.TipoProduto;
import com.example.desafio_nexdom.dto.LucroProdutoDTO;
import com.example.desafio_nexdom.dto.ProdutoResumoDTO;
import com.example.desafio_nexdom.repository.MovimentoEstoqueRepository;
import com.example.desafio_nexdom.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
    }

    // CREATE
    public Produto createProduct(Produto produto) {

        if (produto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do produto não informados"
            );
        }

        if (produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Descrição do produto é obrigatória"
            );
        }

        if (produto.getTipoProduto() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo do produto é obrigatório"
            );
        }

        if (produto.getValorFornecedor() == null || produto.getValorFornecedor() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Valor do fornecedor deve ser maior que zero"
            );
        }

        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantidade em estoque não pode ser negativa"
            );
        }

        return produtoRepository.save(produto);
    }

    // READ
    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }


    public Produto getById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID do produto não informado"
            );
        }

        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));
    }

    // UPDATE
    public Produto updateProduct(Long id, Produto produtoAtualizado) {

        if (produtoAtualizado == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do produto não informados"
            );
        }

        Produto produto = getById(id);

        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setTipoProduto(produtoAtualizado.getTipoProduto());
        produto.setValorFornecedor(produtoAtualizado.getValorFornecedor());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        return produtoRepository.save(produto);
    }

    // DELETE
    public void deleteProduct(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));

        boolean possuiMovimentacao =
                movimentoEstoqueRepository.existsByProdutoId(id);

        if (possuiMovimentacao) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Produto não pode ser removido pois possui movimentações registradas"
            );
        }

        produtoRepository.delete(produto);
    }

    // Consulta de produtos por tipo, com quantidade de saída e quantidade disponível;
    public List<ProdutoResumoDTO> consultarPorTipoComResumo(TipoProduto tipoProduto) {

        if (tipoProduto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de produto não informado"
            );
        }

        List<Produto> produtos = produtoRepository.findByTipoProduto(tipoProduto);

        if (produtos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum produto encontrado para esse tipo"
            );
        }

        return produtos.stream().map(produto -> {

            Integer quantidadeSaida =
                    movimentoEstoqueRepository.somarQuantidadePorProdutoETipo(
                            produto.getId(),
                            TipoMovimentacao.SAIDA
                    );

            return new ProdutoResumoDTO(
                    produto.getId(),
                    produto.getDescricao(),
                    produto.getTipoProduto(),
                    produto.getQuantidadeEstoque(),
                    quantidadeSaida == null ? 0 : quantidadeSaida
            );

        }).toList();
    }

    // Consulta de lucro por produto, exibindo a quantidade total de saída,
    // e total do lucro (valor de venda – valor do fornecedor).
    public LucroProdutoDTO calcularLucro(Long produtoId) {

        if (produtoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID do produto não informado"
            );
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));

        List<MovimentoEstoque> saidas =
                movimentoEstoqueRepository.findByProdutoIdAndTipoMovimentacao(
                        produtoId,
                        TipoMovimentacao.SAIDA
                );

        if (saidas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Produto não possui nenhuma venda registrada. Cadastre uma movimentação de SAÍDA primeiro."
            );
        }

        int quantidadeTotal = saidas.stream()
                .mapToInt(MovimentoEstoque::getQuantidadeMovimentada)
                .sum();

        if (quantidadeTotal <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantidade total vendida inválida"
            );
        }

        double valorTotalVendido = saidas.stream()
                .mapToDouble(m -> m.getValorVenda() * m.getQuantidadeMovimentada())
                .sum();

        double lucroTotal = saidas.stream()
                .mapToDouble(m ->
                        (m.getValorVenda() - produto.getValorFornecedor())
                                * m.getQuantidadeMovimentada()
                )
                .sum();

        return new LucroProdutoDTO(
                produto.getId(),
                produto.getDescricao(),
                quantidadeTotal,
                valorTotalVendido,
                lucroTotal
        );
    }
}
