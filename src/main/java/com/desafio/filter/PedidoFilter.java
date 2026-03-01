package com.desafio.filter;

import lombok.Getter;

@Getter
public class PedidoFilter extends Filter{


    private Long cdPedido;
    private Long cdUsuario;
    private Long cdProduto;

    private boolean cdPedidoAlterado = false;
    private boolean cdUsuarioAlterado = false;
    private boolean cdProdutoAlterado = false;

    
    public PedidoFilter(){
        super(); 

        setNmAtributoOrdenacao("PEDIDO_CD");
        setTpOrdenacao("ASC");
    }
    public PedidoFilter(Long cdPedido){
        super(); 

        setCdPedido(cdPedido);
        setNmAtributoOrdenacao("PEDIDO_CD");
        setTpOrdenacao("ASC");
    }
    
    public void setCdPedido(Long cdPedido){
        this.cdPedido =  cdPedido;
        this.cdPedidoAlterado = true;
    }
    public void setCdUsuario(Long cdUsuario){
        this.cdUsuario =  cdUsuario;
        this.cdUsuarioAlterado = true;
    }
    public void setCdProduto(Long cdProduto){
        this.cdProduto =  cdProduto;
        this.cdProdutoAlterado = true;
    }

    
}
