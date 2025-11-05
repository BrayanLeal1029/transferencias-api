package com.example.transferenciasMonetarias.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("cuentas")
public class Cuenta {

    @Id
    private Integer id;

    private String nombre;

    private Double saldo;

    public Cuenta(String nombre, Double saldo) {
        this.nombre = nombre;
        this.saldo = saldo;
    }
}


