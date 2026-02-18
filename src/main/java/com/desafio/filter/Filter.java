package com.desafio.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Filter {
    private String nmAtributoOrdenacao;
    private String tpOrdenacao;

    private boolean nmAtributoOrdenacaoAlterado = false;
    private boolean tpOrdenacaoAlterado = false;
    

    public void setNmAtributoOrdenacao(String nmAtributoOrdenacao){
        this.nmAtributoOrdenacao =  nmAtributoOrdenacao;
        this.nmAtributoOrdenacaoAlterado = true;
    }
    public void setTpOrdenacao(String tpOrdenacao){
        this.tpOrdenacao =  tpOrdenacao;
        this.tpOrdenacaoAlterado = true;
    }

    public String getOrdencao(){
        return this.nmAtributoOrdenacao + " " + this.tpOrdenacao;
    }

}
