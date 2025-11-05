package com.example.transferenciasMonetarias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaRequest {

    private Integer origenId;
    private Integer destinoId;
    private Double monto;
}
