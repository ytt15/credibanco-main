package com.example.credibanco.repositories;

import com.example.credibanco.models.TransaccionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionModel, Integer> {
    @Query("SELECT t FROM TransaccionModel t WHERE t.tarjetaModel.idTarjeta = :tarjetaId")
    List<TransaccionModel> findByTarjetaId(@Param("tarjetaId") Integer tarjetaId);

    @Transactional
    @Query(value = "{call RealizarCompra2(:numeroTarjeta, :monto)}", nativeQuery = true)
    TransaccionModel realizarCompra2(@Param("numeroTarjeta") int numeroTarjeta, @Param("monto") BigDecimal monto
    );

    @Transactional
    @Query(value = "{call AnularTransaccion(:idTransaction)}", nativeQuery = true)
    String anularTransaccion(@Param("idTransaction") int idTransaction
    );

    List<TransaccionModel> findByEstado(String estado);
}
