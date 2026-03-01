package com.desafio.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="PEDIDO")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PEDIDO_CD", length=4, nullable=false)
    private Long cdPedido;

    @Column(name="CADASTRO_DT", nullable=false)
    private Date dtCadastro;

    @Column(name="PEDIDO_VL", precision=10, scale=2, nullable=false)
    private BigDecimal vlPedido;

    @Column(name="TOTAL_VL", precision=10, scale=2, nullable=false)
    private BigDecimal vlTotal;

    @Column(name="DESCONTO_PC", precision=5, scale=2, nullable=false)
    private BigDecimal pcDesconto;

    @Column(name="USUARIO_CD", length=4, nullable=false)
    private Long cdUsuario;
}
