package com.desafio.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ProdutoFilter extends Filter{


    private Long cdProduto;
    private String nmProduto;
    private String dsProduto;

    private boolean cdProdutoAlterado = false;
    private boolean nmProdutoAlterado = false;
    private boolean dsProdutoAlterado = false;

    
    public ProdutoFilter(){
        super(); 

        setNmAtributoOrdenacao("PRODUTO_CD");
        setTpOrdenacao("ASC");
    }
    public ProdutoFilter(Long cdProduto){
        super(); 

        setCdProduto(cdProduto);
        setNmAtributoOrdenacao("PRODUTO_CD");
        setTpOrdenacao("ASC");
    }
    
    public void setCdProduto(Long cdProduto){
        this.cdProduto =  cdProduto;
        this.cdProdutoAlterado = true;
    }
    public void setNmProduto(String nmProduto){
        this.nmProduto =  nmProduto;
        this.nmProdutoAlterado = true;
    }
    public void setDsProduto(String dsProduto){
        this.dsProduto =  dsProduto;
        this.dsProdutoAlterado = true;
    }

    
}
