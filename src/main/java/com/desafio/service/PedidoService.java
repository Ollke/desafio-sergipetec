package com.desafio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.entity.Pedido;
import com.desafio.entity.PedidoDetalhe;
import com.desafio.entity.Produto;
import com.desafio.entity.ProdutoPK;
import com.desafio.filter.PedidoFilter;
import com.desafio.filter.ProdutoFilter;
import com.desafio.filter.UsuarioFilter;
import com.desafio.repository.pedido.PedidoRepository;
import com.desafio.repository.pedidoDetalhe.PedidoDetalheRepository;
import com.desafio.repository.produto.ProdutoRepository;
import com.desafio.repository.usuario.UsuarioRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoDetalheRepository pedidoDetalheRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoService produtoService;

    public void excluir(Long cdPedido) {
        List<PedidoDetalhe> detalhes = pedidoDetalheRepository.consultar(new PedidoFilter(cdPedido));
        for(PedidoDetalhe detalhe : detalhes){
            Produto produtoBanco = produtoRepository.consultar(new ProdutoFilter(detalhe.getId().getCdProduto())).get(0);
            Produto produtoAlterado = getProdutoAlterado(produtoBanco, detalhe.getQtProduto());

            produtoService.alterar(produtoAlterado);
        }

        pedidoDetalheRepository.excluir(cdPedido);
        pedidoRepository.excluir(cdPedido);
    }

    public void incluir(Pedido pedido, List<PedidoDetalhe> detalhes) {
        if(usuarioRepository.consultar(new UsuarioFilter(pedido.getCdUsuario())).isEmpty())
            throw new RuntimeException("Usuário não encontrado!");

        HashMap<Long,Produto> produtos = new HashMap<Long,Produto>();

        BigDecimal vlSolicitado = BigDecimal.ZERO;
        BigDecimal vlFinal = BigDecimal.ZERO;
        for(PedidoDetalhe detalhe : detalhes){
            List<Produto> produtosBanco = produtoRepository.consultar(new ProdutoFilter(detalhe.getId().getCdProduto()));
            if(produtosBanco.isEmpty())
                throw new RuntimeException("Produto não encontrado: " + detalhe.getId().getCdProduto());

            Produto produtoBanco = produtosBanco.get(0);
            detalhe.getId().setSqProduto(produtoBanco.getId().getSqProduto());

            Produto produtoAlterado = getProdutoAlterado(produtoBanco, detalhe.getQtProduto() * -1);
            produtos.put(detalhe.getId().getCdProduto(), produtoAlterado);

            BigDecimal vlDetalhe = produtoBanco.getVlProduto().multiply(BigDecimal.valueOf(detalhe.getQtProduto()));
            vlSolicitado = vlSolicitado.add(vlDetalhe);
        }

        if(pedido.getPcDesconto().equals(BigDecimal.ZERO)){
            vlFinal = vlSolicitado;
        }else{
            BigDecimal vlPercentual = pedido.getPcDesconto().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            vlFinal = vlSolicitado.subtract(vlSolicitado.multiply(vlPercentual)).setScale(2, RoundingMode.HALF_UP);
        }

        pedido.setVlPedido(vlSolicitado);
        pedido.setVlTotal(vlFinal);

        pedidoRepository.incluir(pedido);
        Long cdPedido = pedidoRepository.consultarMaxCd();

        for(PedidoDetalhe detalhe : detalhes){
            detalhe.getId().setCdPedido(cdPedido);
            pedidoDetalheRepository.incluir(detalhe);

            produtoService.alterar(produtos.get(detalhe.getId().getCdProduto()));
        }
    }

    public Produto getProdutoAlterado(Produto produtoBanco, Long qtProduto){
        Produto produto = new Produto();

        produto.setId(new ProdutoPK());
        produto.getId().setCdProduto(produtoBanco.getId().getCdProduto());
        produto.setNmProduto(produtoBanco.getNmProduto());
        produto.setDsProduto(produtoBanco.getDsProduto());
        produto.setVlProduto(produtoBanco.getVlProduto());

        produto.setQtProduto(produtoBanco.getQtProduto() + qtProduto);

        return produto;
    }

}
