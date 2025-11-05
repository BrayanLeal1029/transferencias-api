package com.example.transferenciasMonetarias.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("transferencias")
public class Transferencia {

    @Id
    private Integer id;

    @Column("origen_id")
    private Integer origenId;

    @Column("destino_id")
    private Integer destinoId;

    private Double monto;

    private LocalDateTime fecha;

    public Transferencia(Integer origenId, Integer destinoId, Double monto) {
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }
}


