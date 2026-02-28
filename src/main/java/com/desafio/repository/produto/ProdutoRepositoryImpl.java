package com.desafio.repository.produto;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder.Entity;
import org.springframework.stereotype.Repository;

import com.desafio.entity.Produto;
import com.desafio.filter.ProdutoFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Produto> consultar(ProdutoFilter filter) {
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM PRODUTO"
        );

        StringBuilder filtroStr = new StringBuilder(" WHERE ATIVO_FL = true ");
        String conexao = " AND ";

        if (filter.isCdProdutoAlterado()) {
            filtroStr.append(conexao + " PRODUTO_CD = :cdProduto ");
        }
        if (filter.isNmProdutoAlterado()) {
            filtroStr.append(conexao + " PRODUTO_NM LIKE :nmProduto ");
        }
        if (filter.isDsProdutoAlterado()) {
            filtroStr.append(conexao + " PRODUTO_DS LIKE :dsProduto ");
        }

        if(!conexao.isEmpty())
            sql.append(filtroStr);

        sql.append(" ORDER BY " + filter.getOrdencao());

        Query query = entityManager.createNativeQuery(sql.toString(), Produto.class);

        if (filter.isCdProdutoAlterado()) query.setParameter("cdProduto", filter.getCdProduto());
        if (filter.isNmProdutoAlterado()) query.setParameter("nmProduto", filter.getNmProduto());
        if (filter.isDsProdutoAlterado()) query.setParameter("dsProduto", filter.getDsProduto());

        return query.getResultList();
    }

    @Override
    @Transactional
    public void incluir(Produto produto){
        StringBuilder sql = new StringBuilder("INSERT INTO PRODUTO (CADASTRO_DT, PRODUTO_CD, PRODUTO_SQ, PRODUTO_NM, PRODUTO_DS, PRODUTO_QT, PRODUTO_VL, ATIVO_FL) VALUES (CURRENT_TIMESTAMP, :cdProduto, :sqProduto, :nmProduto, :dsProduto, :qtProduto, :vlProduto, true);");

        Query query = entityManager.createNativeQuery(sql.toString(), Produto.class);
        
        query.setParameter("cdProduto", produto.getId().getCdProduto());
        query.setParameter("sqProduto", produto.getId().getSqProduto());
        query.setParameter("nmProduto", produto.getNmProduto());
        query.setParameter("dsProduto", produto.getDsProduto());
        query.setParameter("qtProduto", produto.getQtProduto());
        query.setParameter("vlProduto", produto.getVlProduto());

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    @Modifying
    public void excluir(Long cdProduto){
        StringBuilder sql = new StringBuilder("UPDATE PRODUTO SET ATIVO_FL = false WHERE PRODUTO_CD = :cdProduto;");

        Query query = entityManager.createNativeQuery(sql.toString(), Produto.class);
        query.setParameter("cdProduto", cdProduto);

        query.executeUpdate();
    }
}