package com.example.transferenciasMonetarias.service;

import com.example.transferenciasMonetarias.model.Cuenta;
import com.example.transferenciasMonetarias.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public Flux<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    public Mono<Cuenta> obtenerPorId(Integer id) {
        return cuentaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con id: " + id)));
    }

    public Mono<Cuenta> guardar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Mono<Cuenta> actualizar(Integer id, Cuenta cuenta) {
        return cuentaRepository.findById(id)
                .flatMap(cuentaExistente -> {
                    cuentaExistente.setNombre(cuenta.getNombre());
                    cuentaExistente.setSaldo(cuenta.getSaldo());
                    return cuentaRepository.save(cuentaExistente);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con id: " + id)));
    }

    public Mono<Void> eliminar(Integer id) {
        return cuentaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con id: " + id)))
                .flatMap(cuenta -> cuentaRepository.delete(cuenta));
    }

    public Mono<Boolean> tieneSaldoSuficiente(Integer id, Double monto) {
        return cuentaRepository.findById(id)
                .map(cuenta -> cuenta.getSaldo() >= monto)
                .defaultIfEmpty(false);
    }
}

