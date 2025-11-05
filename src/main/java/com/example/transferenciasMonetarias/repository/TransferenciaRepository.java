package com.example.transferenciasMonetarias.repository;

import com.example.transferenciasMonetarias.model.Transferencia;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransferenciaRepository extends ReactiveCrudRepository<Transferencia, Integer> {

    @Query("SELECT * FROM transferencias WHERE origen_id = :cuentaId OR destino_id = :cuentaId ORDER BY fecha DESC")
    Flux<Transferencia> findByCuentaId(Integer cuentaId);

    @Query("SELECT * FROM transferencias ORDER BY fecha DESC")
    Flux<Transferencia> findAllOrderByFechaDesc();
}


