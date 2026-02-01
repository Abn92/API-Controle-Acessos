package com.example.desafio_nexdom.service;

import com.example.desafio_nexdom.domain.entity.MovimentoEstoque;
import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoMovimentacao;
import com.example.desafio_nexdom.domain.entity.*;
import com.example.desafio_nexdom.repository.MovimentoEstoqueRepository;
import com.example.desafio_nexdom.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentoEstoqueRepository movimentoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void deveLancarErroQuandoEstoqueForInsuficiente() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidadeEstoque(1);

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> estoqueService.movimentarEstoque(
                        1L,
                        TipoMovimentacao.SAIDA,
                        5,
                        100.0
                )
        );

        assertEquals("Estoque insuficiente para saída", exception.getMessage());

        verify(movimentoRepository, never()).save(any());
    }

    @Test
    void devePermitirSaidaQuandoEstoqueForSuficiente() {
        // Arrange
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidadeEstoque(10);

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        // Act
        estoqueService.movimentarEstoque(
                1L,
                TipoMovimentacao.SAIDA,
                3,
                500.0
        );

        // Assert
        assertEquals(7, produto.getQuantidadeEstoque());

        verify(produtoRepository).save(produto);
        verify(movimentoRepository).save(any(MovimentoEstoque.class));
    }

}
