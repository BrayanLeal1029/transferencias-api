package com.example.transferenciasMonetarias.controller;

import com.example.transferenciasMonetarias.dto.TransferenciaRequest;
import com.example.transferenciasMonetarias.model.Transferencia;
import com.example.transferenciasMonetarias.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transferencia> transferir(@RequestBody TransferenciaRequest request) {
        return transferenciaService.realizarTransferencia(
                request.getOrigenId(),
                request.getDestinoId(),
                request.getMonto()
        );
    }

    @GetMapping
    public Flux<Transferencia> listarTransferencias() {
        return transferenciaService.listarTransferencias();
    }

    @GetMapping("/{id}")
    public Mono<Transferencia> obtenerPorId(@PathVariable Integer id) {
        return transferenciaService.obtenerPorId(id);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public Flux<Transferencia> listarPorCuenta(@PathVariable Integer cuentaId) {
        return transferenciaService.listarPorCuenta(cuentaId);
    }
}


