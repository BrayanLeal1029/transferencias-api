package com.example.transferenciasMonetarias.controller;

import com.example.transferenciasMonetarias.model.Cuenta;
import com.example.transferenciasMonetarias.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public Flux<Cuenta> listar() {
        return cuentaService.listar()
                .doOnSubscribe(s -> System.out.println("🔍 Iniciando consulta de cuentas"))
                .doOnNext(cuenta -> System.out.println("✅ Cuenta encontrada: " + cuenta))
                .doOnComplete(() -> System.out.println("✅ Consulta completada"))
                .doOnError(error -> System.err.println("❌ Error: " + error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<Cuenta> obtenerPorId(@PathVariable Integer id) {
        return cuentaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cuenta> crear(@RequestBody Cuenta cuenta) {
        return cuentaService.guardar(cuenta);
    }

    @PutMapping("/{id}")
    public Mono<Cuenta> actualizar(@PathVariable Integer id, @RequestBody Cuenta cuenta) {
        return cuentaService.actualizar(id, cuenta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable Integer id) {
        return cuentaService.eliminar(id);
    }
}

