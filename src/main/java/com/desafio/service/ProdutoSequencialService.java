package com.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.repository.produtoSequencial.ProdutoSequencialRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoSequencialService {
    
    @Autowired
    private ProdutoSequencialRepository produtoSequencialServiceRepository;
    

    @Transactional
    public Long getProximoCd() {
        Long cdProduto = produtoSequencialServiceRepository.consultarProximoCd();
        produtoSequencialServiceRepository.incluir(cdProduto);

        return cdProduto;
    }

    @Transactional
    public Long getProximoSq(Long cdProduto) {
        Long sqProduto = produtoSequencialServiceRepository.consultarProximoSq(cdProduto);
        produtoSequencialServiceRepository.adcionarSq(cdProduto);

        return sqProduto;
    }
}
