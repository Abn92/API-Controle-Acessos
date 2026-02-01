package com.example.desafio_nexdom.controller;

import com.example.desafio_nexdom.domain.entity.Produto;
import com.example.desafio_nexdom.domain.enums.TipoProduto;
import com.example.desafio_nexdom.dto.LucroProdutoDTO;
import com.example.desafio_nexdom.dto.ProdutoRequest;
import com.example.desafio_nexdom.dto.ProdutoResumoDTO;
import com.example.desafio_nexdom.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "CRUD de produtos e consultas de estoque e lucro")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
            summary = "Listar todos os produtos",
            description = "Retorna todos os produtos cadastrados"
    )
    @GetMapping
    public List<Produto> getAllProducts() {
        return produtoService.listAll();
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os dados de um produto específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public Produto getProductById(@PathVariable Long id) {
        return produtoService.getById(id);
    }

    @Operation(
            summary = "Cadastrar produto",
            description = "Cria um novo produto no sistema"
    )
    @PostMapping
    public Produto create(@RequestBody ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setDescricao(request.getDescricao());
        produto.setTipoProduto(request.getTipoProduto());
        produto.setValorFornecedor(request.getValorFornecedor());
        produto.setQuantidadeEstoque(request.getQuantidadeEstoque());

        return produtoService.createProduct(produto);
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public Produto update(
            @PathVariable Long id,
            @RequestBody Produto produtoAtualizado
    ) {
        return produtoService.updateProduct(id, produtoAtualizado);
    }

    @Operation(
            summary = "Remover produto",
            description = "Exclui um produto pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produtoService.deleteProduct(id);
    }

    @Operation(
            summary = "Consultar produtos por tipo (com resumo)",
            description = "Lista produtos por tipo, exibindo quantidade em estoque e quantidade total de saída"
    )
    @GetMapping("/tipo/{tipo}/resumo")
    public List<ProdutoResumoDTO> consultarPorTipoResumo(
            @PathVariable TipoProduto tipo
    ) {
        return produtoService.consultarPorTipoComResumo(tipo);
    }

    @Operation(
            summary = "Consultar lucro por produto",
            description = "Exibe a quantidade total de saída e o lucro total do produto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lucro calculado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}/lucro")
    public LucroProdutoDTO lucroPorProduto(@PathVariable Long id) {
        return produtoService.calcularLucro(id);
    }
}
