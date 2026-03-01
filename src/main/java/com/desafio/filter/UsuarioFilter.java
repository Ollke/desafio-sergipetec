package com.desafio.filter;

import lombok.Getter;

@Getter
public class UsuarioFilter extends Filter{


    private Long cdUsuario;
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
    public UsuarioFilter(Long cdUsuario){
        super(); 

        setCdUsuario(cdUsuario);
        setNmAtributoOrdenacao("USUARIO_CD");
        setTpOrdenacao("ASC");
    }
    
    public void setCdUsuario(Long cdUsuario){
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
