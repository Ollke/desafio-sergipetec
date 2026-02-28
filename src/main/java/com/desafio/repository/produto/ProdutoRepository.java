package com.desafio.repository.produto;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import com.desafio.entity.Produto;
import com.desafio.filter.ProdutoFilter;

public interface ProdutoRepository {
    List<Produto> consultar(ProdutoFilter filter);

    void incluir(Produto produto);

    @Modifying
    void excluir(Long cdProduto);
}