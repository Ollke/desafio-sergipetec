package com.desafio.repository.usuario;

import java.util.List;

import com.desafio.entity.Usuario;
import com.desafio.filter.UsuarioFilter;

public interface UsuarioRepository {
    List<Usuario> consultar(UsuarioFilter filter);

    void incluir(Usuario usuario);

    void alterar(Usuario usuario);

    void excluir(Integer cdUsuario);
}