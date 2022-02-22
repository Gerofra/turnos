package com.turnos.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turnos.entities.Rol;
import com.turnos.enums.RolEnum;

@Repository
public interface RolRepo extends JpaRepository<Rol, Integer> {

	Optional<Rol> findByRolEnum(RolEnum rolEnum);
	boolean existsByRolEnum(RolEnum rolEnum);
}
