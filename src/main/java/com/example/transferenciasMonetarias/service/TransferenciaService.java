package com.example.transferenciasMonetarias.service;

import com.example.transferenciasMonetarias.model.Transferencia;
import com.example.transferenciasMonetarias.repository.CuentaRepository;
import com.example.transferenciasMonetarias.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final CuentaRepository cuentaRepository;

    @Transactional
    public Mono<Transferencia> realizarTransferencia(Integer origenId, Integer destinoId, Double monto) {
        // Validaciones
        if (origenId.equals(destinoId)) {
            return Mono.error(new RuntimeException("No se puede transferir a la misma cuenta"));
        }

        if (monto <= 0) {
            return Mono.error(new RuntimeException("El monto debe ser mayor a 0"));
        }

        // Verificar que ambas cuentas existen
        return Mono.zip(
                        cuentaRepository.findById(origenId)
                                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta origen no encontrada"))),
                        cuentaRepository.findById(destinoId)
                                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta destino no encontrada")))
                )
                .flatMap(cuentas -> {
                    // Verificar saldo suficiente
                    if (cuentas.getT1().getSaldo() < monto) {
                        return Mono.error(new RuntimeException("Saldo insuficiente en cuenta origen"));
                    }

                    // Realizar la transferencia
                    return cuentaRepository.debitar(origenId, monto)
                            .flatMap(debitado -> {
                                if (debitado == 0) {
                                    return Mono.error(new RuntimeException("No se pudo debitar de la cuenta origen"));
                                }
                                return cuentaRepository.acreditar(destinoId, monto);
                            })
                            .flatMap(acreditado -> {
                                if (acreditado == 0) {
                                    return Mono.error(new RuntimeException("No se pudo acreditar a la cuenta destino"));
                                }
                                // Guardar el registro de la transferencia
                                Transferencia transferencia = new Transferencia(origenId, destinoId, monto);
                                transferencia.setFecha(LocalDateTime.now());
                                return transferenciaRepository.save(transferencia);
                            });
                });
    }

    public Flux<Transferencia> listarTransferencias() {
        return transferenciaRepository.findAllOrderByFechaDesc();
    }

    public Flux<Transferencia> listarPorCuenta(Integer cuentaId) {
        return transferenciaRepository.findByCuentaId(cuentaId);
    }

    public Mono<Transferencia> obtenerPorId(Integer id) {
        return transferenciaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Transferencia no encontrada con id: " + id)));
    }
}



