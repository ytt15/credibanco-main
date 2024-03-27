package com.example.credibanco.repositories;

import com.example.credibanco.models.TarjetaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends CrudRepository<TarjetaModel, Long> {
}
