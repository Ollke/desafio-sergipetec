package com.desafio.repository.pedido;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.desafio.entity.Pedido;
import com.desafio.entity.ProdutoSequencial;
import com.desafio.entity.Usuario;
import com.desafio.filter.PedidoFilter;
import com.desafio.filter.UsuarioFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pedido> consultar(PedidoFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM PEDIDO ");
        StringBuilder filtroStr = new StringBuilder("WHERE ");
        String conexao = "";

        if (filter.isCdPedidoAlterado()) {
            filtroStr.append(conexao + " PEDIDO.PEDIDO_CD = :cdPedido ");
            conexao = "AND";
        }
        if (filter.isCdUsuarioAlterado()) {
            filtroStr.append(conexao + " PEDIDO.USUARIO_CD = :cdUsuario ");
            conexao = "AND";
        }
        if (filter.isCdProdutoAlterado()) {
            filtroStr.append(conexao + " 0 <> (SELECT COUNT(*) FROM PEDIDO_DETALHE WHERE PEDIDO.PEDIDO_CD = PEDIDO_DETALHE.PEDIDO_CD AND PEDIDO_DETALHE.PRODUTO_CD = :cdProduto) ");
            conexao = "AND";
        }
        if (filter.isDtInicioAlterado() && filter.isDtFimAlterado()) {
            filtroStr.append(conexao + " PEDIDO.CADASTRO_DT BETWEEN :dtInicio AND :dtFim ");
            conexao = "AND";
        }

        if(!conexao.isEmpty()) sql.append(filtroStr);
        sql.append(" ORDER BY " + filter.getOrdencao());

        Query query = entityManager.createNativeQuery(sql.toString(), Pedido.class);

        if (filter.isCdPedidoAlterado()) query.setParameter("cdPedido", filter.getCdPedido());
        if (filter.isCdUsuarioAlterado()) query.setParameter("cdUsuario", filter.getCdUsuario());
        if (filter.isCdProdutoAlterado()) query.setParameter("cdProduto", filter.getCdProduto());
        if (filter.isDtInicioAlterado() && filter.isDtFimAlterado()) {
            query.setParameter("dtInicio", filter.getDtInicio());
            query.setParameter("dtFim", filter.getDtFim());
        }

        return query.getResultList();
    }

    @Override
    public Long consultarMaxCd() {
        StringBuilder sql = new StringBuilder("SELECT MAX(PEDIDO_CD) AS PEDIDO_CD FROM PEDIDO ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(org.hibernate.transform.AliasToEntityMapResultTransformer.INSTANCE);

        try {
            List<Map<String, Object>> retorno = query.getResultList();
            return Long.valueOf(String.valueOf(retorno.get(0).get("pedido_cd")));
        } catch (Exception e) {
            return Long.valueOf(1);
        }
    }
    
    @Override
    @Transactional
    public void incluir(Pedido pedido){
        StringBuilder sql = new StringBuilder("INSERT INTO PEDIDO (CADASTRO_DT, PEDIDO_VL, TOTAL_VL, DESCONTO_PC, USUARIO_CD) VALUES (:dtCadastro, :vlPedido, :vlTotal, :pcDesconto, :cdUsuario);");

        Query query = entityManager.createNativeQuery(sql.toString(), Pedido.class);
        
        query.setParameter("dtCadastro", pedido.getDtCadastro());
        query.setParameter("vlPedido", pedido.getVlPedido());
        query.setParameter("vlTotal", pedido.getVlTotal());
        query.setParameter("pcDesconto", pedido.getPcDesconto());
        query.setParameter("cdUsuario", pedido.getCdUsuario());

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    public void excluir(Long cdPedido){
        StringBuilder sql = new StringBuilder("DELETE FROM PEDIDO WHERE PEDIDO_CD = :cdPedido");

        Query query = entityManager.createNativeQuery(sql.toString(), Pedido.class);
        
        query.setParameter("cdPedido", cdPedido);

        query.executeUpdate();
    }
}