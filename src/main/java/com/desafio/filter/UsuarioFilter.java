package com.desafio.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UsuarioFilter extends Filter{


    private Integer cdUsuario;
    private String nmUsuario;
    private String email;

    private boolean cdUsuarioAlterado = false;
    private boolean nmUsuarioAlterado = false;
    private boolean emailAlterado = false;

    
    public UsuarioFilter(){
        super(); 

        setNmAtributoOrdenacao("USUARIO_CD");
        setTpOrdenacao("ASC");
    }
    public UsuarioFilter(Integer cdUsuario){
        super(); 

        setCdUsuario(cdUsuario);
        setNmAtributoOrdenacao("USUARIO_CD");
        setTpOrdenacao("ASC");
    }
    
    public void setCdUsuario(Integer cdUsuario){
        this.cdUsuario =  cdUsuario;
        this.cdUsuarioAlterado = true;
    }
    public void setNmUsuario(String nmUsuario){
        this.nmUsuario =  nmUsuario;
        this.nmUsuarioAlterado = true;
    }
    public void setEmail(String email){
        this.email =  email;
        this.emailAlterado = true;
    }

    
}
