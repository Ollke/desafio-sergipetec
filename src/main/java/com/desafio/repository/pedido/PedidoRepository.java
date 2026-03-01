package com.desafio.repository.pedido;

import java.util.List;

import com.desafio.entity.Pedido;
import com.desafio.filter.PedidoFilter;

public interface PedidoRepository {
    List<Pedido> consultar(PedidoFilter filter);
    
    Long consultarMaxCd();

    void incluir(Pedido usuario);

    void excluir(Long cdPedido);
}