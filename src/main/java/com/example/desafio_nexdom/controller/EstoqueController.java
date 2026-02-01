package com.example.desafio_nexdom.controller;

import com.example.desafio_nexdom.dto.MovimentoEstoqueRequest;
import com.example.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.example.desafio_nexdom.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Estoque", description = "Operações de entrada e saída de produtos no estoque")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @Operation(
            summary = "Movimentar estoque",
            description = "Realiza entrada ou saída de produtos no estoque. " +
                    "Na saída, valida se há quantidade suficiente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quantidade insuficiente em estoque"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping("/movimentar")
    public void movimentar(
            @RequestBody MovimentoEstoqueRequest request
    ) {
        estoqueService.movimentarEstoque(
                request.getProdutoId(),
                request.getTipoMovimentacao(),
                request.getQuantidade(),
                request.getValorVenda()
        );
    }

    @GetMapping("/movimentacoes")
    public List<MovimentoEstoqueResponseDTO> listarMovimentacoes(
    ) {
        return estoqueService.listAll();
    }
}
