package com.desafio.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="PRODUTO")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Produto {

    // @Column(name="PRODUTO_CD", length=4, nullable=false)
    // private Long cdProduto;

    // @Column(name="PRODUTO_SQ", length=4, nullable=false)
    // private Long sqProduto;

    @EmbeddedId
    private ProdutoPK id;
    
    @Column(name="PRODUTO_NM", length=50, nullable=false, unique=false)
    private String nmProduto;
    
    @Column(name="PRODUTO_DS", length=50, nullable=false, unique=false)
    private String dsProduto;
    
    @Column(name="PRODUTO_QT", length=10, nullable=false, unique=false)
    private Long qtProduto;

    @Column(name="PRODUTO_VL", precision=10, scale=2, nullable=false)
    private BigDecimal vlProduto;

    @Column(name="CADASTRO_DT", nullable=false, unique=false)
    private Date dtCadastro;

    @Column(name="ATIVO_FL", nullable=false, unique=false)
    private boolean flAtivo;
}
