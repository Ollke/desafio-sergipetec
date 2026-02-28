package com.desafio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="PRODUTO_SEQUENCIAL")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoSequencial {

    @Id 
    @Column(name="PRODUTO_CD", length=4, nullable=false)
    private Long cdProduto;

    @Column(name="PRODUTO_SQ", length=4, nullable=false)
    private Long sqProduto;
}
