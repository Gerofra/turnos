package com.turnos.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turnos.entities.Pago;

public interface PagoRepo extends JpaRepository<Pago, Integer> {

}
