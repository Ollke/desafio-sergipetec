package com.desafio.repository.produtoSequencial;

public interface ProdutoSequencialRepository {
    Long consultarProximoCd();

    Long consultarProximoSq(Long cdProduto);

    void incluir(Long cdProduto);

    void adcionarSq(Long cdProduto);
}