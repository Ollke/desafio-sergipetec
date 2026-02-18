package com.desafio.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="USUARIO")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cdUsuario")
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USUARIO_CD", length=4, nullable=false)
    private Long cdUsuario;
    
    @Column(name="USUARIO_NM", length=50, nullable=false, unique=false)
    private String nmUsuario;
    
    @Column(name="EMAIL", nullable=false, unique=false)
    private String email;

    @Column(name="CADASTRO_DT", nullable=false, unique=false)
    private Date dtCadastro;
}
