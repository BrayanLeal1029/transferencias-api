package com.example.transferenciasMonetarias.repository;

import com.example.transferenciasMonetarias.model.Cuenta;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CuentaRepository extends ReactiveCrudRepository<Cuenta, Integer> {

    @Modifying
    @Query("UPDATE cuentas SET saldo = saldo - :monto WHERE id = :id AND saldo >= :monto")
    Mono<Integer> debitar(Integer id, Double monto);

    @Modifying
    @Query("UPDATE cuentas SET saldo = saldo + :monto WHERE id = :id")
    Mono<Integer> acreditar(Integer id, Double monto);

    @Query("SELECT * FROM cuentas WHERE id = :id FOR UPDATE")
    Mono<Cuenta> findByIdForUpdate(Integer id);
}



