package com.desafio.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class ProdutoPK implements Serializable {
    @Column(name="PRODUTO_CD", length=4, nullable=false)
    private Long cdProduto;

    @Column(name="PRODUTO_SQ", length=4, nullable=false)
    private Long sqProduto;
}
