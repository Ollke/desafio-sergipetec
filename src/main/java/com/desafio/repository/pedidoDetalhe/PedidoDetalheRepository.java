package com.desafio.repository.pedidoDetalhe;

import java.util.List;
import java.util.Map;

import com.desafio.entity.PedidoDetalhe;
import com.desafio.filter.PedidoFilter;

public interface PedidoDetalheRepository {
    List<PedidoDetalhe> consultar(PedidoFilter filter);

    List<Map<String, Object>> consultarHash(PedidoFilter filter);

    void incluir(PedidoDetalhe usuario);

    void excluir(Long cdPedido);
}