package com.desafio.repository.pedidoDetalhe;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.desafio.entity.Pedido;
import com.desafio.entity.PedidoDetalhe;
import com.desafio.filter.PedidoFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class PedidoDetalheRepositoryImpl implements PedidoDetalheRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PedidoDetalhe> consultar(PedidoFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM PEDIDO_DETALHE ");
        StringBuilder filtroStr = new StringBuilder("WHERE ");

        if (filter.isCdPedidoAlterado()) {
            filtroStr.append(" PEDIDO_DETALHE.PEDIDO_CD = :cdPedido ");
        }

        
        if (filter.isCdPedidoAlterado()) sql.append(filtroStr);
        sql.append(" ORDER BY " + filter.getOrdencao());

        Query query = entityManager.createNativeQuery(sql.toString(), PedidoDetalhe.class);

        if (filter.isCdPedidoAlterado()) query.setParameter("cdPedido", filter.getCdPedido());

        return query.getResultList();
    }

    public List<Map<String, Object>> consultarHash(PedidoFilter filter) {
        StringBuilder sql = new StringBuilder(
            "SELECT PEDIDO_DETALHE.PEDIDO_CD, "+
            "PEDIDO_DETALHE.PRODUTO_CD, "+
            "PEDIDO_DETALHE.PRODUTO_QT, "+
            "PRODUTO.PRODUTO_NM "+
            "FROM PEDIDO_DETALHE " +
            "JOIN PRODUTO ON PRODUTO.PRODUTO_CD = PEDIDO_DETALHE.PRODUTO_CD AND PRODUTO.PRODUTO_SQ = PEDIDO_DETALHE.PRODUTO_SQ "
        );
        StringBuilder filtroStr = new StringBuilder("WHERE ");
        String conexao = "";

        if (filter.isCdPedidoAlterado()) {
            filtroStr.append(conexao + " PEDIDO_DETALHE.PEDIDO_CD = :cdPedido ");
            conexao = "AND";
        }

        if(!conexao.isEmpty())
            sql.append(filtroStr);

        sql.append(" ORDER BY " + filter.getOrdencao());

        Query query = entityManager.createNativeQuery(sql.toString());
        query.unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(org.hibernate.transform.AliasToEntityMapResultTransformer.INSTANCE);

        if (filter.isCdPedidoAlterado()) query.setParameter("cdPedido", filter.getCdPedido());

        return query.getResultList();
    }
    
    @Override
    @Transactional
    public void incluir(PedidoDetalhe pedidoDetalhe){
        StringBuilder sql = new StringBuilder("INSERT INTO PEDIDO_DETALHE (PEDIDO_CD, PRODUTO_CD, PRODUTO_SQ, PRODUTO_QT) VALUES (:cdPedido, :cdProduto, :sqProduto, :qtProduto);");

        Query query = entityManager.createNativeQuery(sql.toString(), PedidoDetalhe.class);
        
        query.setParameter("cdPedido", pedidoDetalhe.getId().getCdPedido());
        query.setParameter("cdProduto", pedidoDetalhe.getId().getCdProduto());
        query.setParameter("sqProduto", pedidoDetalhe.getId().getSqProduto());
        query.setParameter("qtProduto", pedidoDetalhe.getQtProduto());

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    public void excluir(Long cdPedido){
        StringBuilder sql = new StringBuilder("DELETE FROM PEDIDO_DETALHE WHERE PEDIDO_CD = :cdPedido");

        Query query = entityManager.createNativeQuery(sql.toString(), Pedido.class);
        
        query.setParameter("cdPedido", cdPedido);

        query.executeUpdate();
    }
}