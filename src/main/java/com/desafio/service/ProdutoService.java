package com.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.entity.Produto;
import com.desafio.entity.ProdutoPK;
import com.desafio.filter.ProdutoFilter;
import com.desafio.repository.produto.ProdutoRepository;
import com.desafio.repository.produto.ProdutoRepositoryImpl;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoSequencialService produtoSequencialService;

    public void alterar(Produto produto) {
        Long cdProduto = Long.valueOf(produto.getId().getCdProduto());
        Long sqProduto = produtoSequencialService.getProximoSq(cdProduto);

        produto.getId().setSqProduto(sqProduto);

        produtoRepository.excluir(cdProduto);
        produtoRepository.incluir(produto);
    }

    public void incluir(Produto produto) {
        ProdutoPK pk = new ProdutoPK(
            produtoSequencialService.getProximoCd(), 
            Long.valueOf(1)
        );

        produto.setId(pk);
        produtoRepository.incluir(produto);
    }
}
