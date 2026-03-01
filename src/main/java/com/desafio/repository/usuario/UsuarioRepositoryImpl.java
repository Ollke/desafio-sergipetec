package com.desafio.repository.usuario;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.desafio.entity.Usuario;
import com.desafio.filter.UsuarioFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> consultar(UsuarioFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM USUARIO ");
        StringBuilder filtroStr = new StringBuilder("WHERE ");
        String conexao = "";

        if (filter.isCdUsuarioAlterado()) {
            filtroStr.append(conexao + " USUARIO_CD = :cdUsuario ");
            conexao = "AND";
        }
        if (filter.isNmUsuarioAlterado()) {
            filtroStr.append(conexao + " USUARIO_NM = :nmUsuario ");
            conexao = "AND";
        }
        if (filter.isEmailAlterado()) {
            filtroStr.append(conexao + " EMAIL = :email ");
            conexao = "AND";
        }

        if(!conexao.isEmpty())
            sql.append(filtroStr);

        sql.append(" ORDER BY " + filter.getOrdencao());

        Query query = entityManager.createNativeQuery(sql.toString(), Usuario.class);

        if (filter.isCdUsuarioAlterado()) query.setParameter("cdUsuario", filter.getCdUsuario());
        if (filter.isNmUsuarioAlterado()) query.setParameter("nmUsuario", filter.getNmUsuario());
        if (filter.isEmailAlterado()) query.setParameter("email", filter.getEmail());

        return query.getResultList();
    }
    
    @Override
    @Transactional
    public void incluir(Usuario usuario){
        StringBuilder sql = new StringBuilder("INSERT INTO USUARIO (CADASTRO_DT,USUARIO_NM,EMAIL) VALUES (CURRENT_TIMESTAMP, :nmUsuario, :email);");

        Query query = entityManager.createNativeQuery(sql.toString(), Usuario.class);
        
        query.setParameter("nmUsuario", usuario.getNmUsuario());
        query.setParameter("email", usuario.getEmail());

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    public void alterar(Usuario usuario){
        StringBuilder sql = new StringBuilder("UPDATE USUARIO SET USUARIO_NM = :nmUsuario, EMAIL = :email WHERE USUARIO_CD = :cdUsuario;");

        Query query = entityManager.createNativeQuery(sql.toString(), Usuario.class);
        
        query.setParameter("cdUsuario", usuario.getCdUsuario());
        query.setParameter("nmUsuario", usuario.getNmUsuario());
        query.setParameter("email", usuario.getEmail());

        query.executeUpdate();
    }
    
    @Override
    @Transactional
    public void excluir(Long cdUsuario){
        StringBuilder sql = new StringBuilder("DELETE FROM USUARIO WHERE USUARIO_CD = :cdUsuario");

        Query query = entityManager.createNativeQuery(sql.toString(), Usuario.class);
        
        query.setParameter("cdUsuario", cdUsuario);

        query.executeUpdate();
    }
}