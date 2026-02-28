package com.desafio.repository.produtoSequencial;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.desafio.entity.ProdutoSequencial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoSequencialRepositoryImpl implements ProdutoSequencialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Long consultarProximoCd() {
        StringBuilder sql = new StringBuilder(
            "SELECT (coalesce(MAX(PRODUTO_CD),0) + 1) AS PRODUTO_CD, 1 as PRODUTO_SQ FROM PRODUTO_SEQUENCIAL"
        );

        Query query = entityManager.createNativeQuery(sql.toString(), ProdutoSequencial.class);

        try {
            List<ProdutoSequencial> retorno = query.getResultList();
            return (retorno.get(0)).getCdProduto();
        } catch (Exception e) {
            return Long.valueOf(1);
        }
    }

    @Override
    @Transactional
    public Long consultarProximoSq(Long cdProduto) {
        StringBuilder sql = new StringBuilder(
            "SELECT PRODUTO_CD, (coalesce(PRODUTO_SQ, 0) + 1)  AS PRODUTO_SQ FROM PRODUTO_SEQUENCIAL WHERE PRODUTO_CD = :cdProduto"
        );

        Query query = entityManager.createNativeQuery(sql.toString(), ProdutoSequencial.class);
        query.setParameter("cdProduto", cdProduto);

        try {
            return ((ProdutoSequencial) query.getResultList().get(0)).getSqProduto();
        } catch (Exception e) {
            return Long.valueOf(1);
        }
    }

    @Override
    @Transactional
    public void incluir(Long cdProduto){
        StringBuilder sql = new StringBuilder("INSERT INTO PRODUTO_SEQUENCIAL (PRODUTO_CD, PRODUTO_SQ) VALUES (:cdProduto, 1);");

        Query query = entityManager.createNativeQuery(sql.toString(), ProdutoSequencial.class);
        
        query.setParameter("cdProduto", cdProduto);

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    public void adcionarSq(Long cdProduto){
        StringBuilder sql = new StringBuilder("UPDATE PRODUTO_SEQUENCIAL SET PRODUTO_SQ = PRODUTO_SQ + 1 WHERE PRODUTO_CD = :cdProduto;");

        Query query = entityManager.createNativeQuery(sql.toString(), ProdutoSequencial.class);
        
        query.setParameter("cdProduto", cdProduto);

        query.executeUpdate();
    }
}