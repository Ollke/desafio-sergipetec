package com.desafio.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="PEDIDO_DETALHE")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalhe {

    @EmbeddedId
    private PedidoDetalhePK id;
    
    @Column(name="PRODUTO_QT", length=10, nullable=false, unique=false)
    private Long qtProduto;
}
