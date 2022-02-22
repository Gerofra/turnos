package com.turnos.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turnos.entities.Turno;
import com.turnos.entities.Usuario;

@Repository
public interface TurnoRepo extends JpaRepository<Turno, Long> {

	Optional<Turno> findById(String id);
	boolean existsById(String id);
	
	@Query("SELECT c FROM Turno c WHERE c.mes = :mes AND c.empleado = :empleado")
	public List<Turno> turnosEmpleadoMes(@Param("mes") Integer mes, @Param("empleado") Usuario empleado);
	
	@Query("SELECT c FROM Turno c WHERE c.mes = :mes AND c.dia = :dia AND c.empleado = :empleado")
	public List<Turno> turnosEmpleado(@Param("mes") Integer mes, @Param("dia") Integer dia, @Param("empleado") Usuario empleado);
	
	@Query("SELECT DISTINCT c FROM Turno c WHERE c.mes = :mes AND c.dia = :dia AND c.empleado = :empleado AND c.hora = :hora ")
	public List<Turno> existeTurno(@Param("mes") Integer mes, @Param("dia") Integer dia, @Param("hora") String hora, @Param("empleado") Usuario empleado);
	
	@Query("SELECT DISTINCT c FROM Turno c WHERE c.mes = :mes AND c.dia = :dia AND c.empleado = :empleado AND c.anio = :anio AND c.cliente = null")
	public List<Turno> turnosLibres(@Param("mes") Integer mes, @Param("dia") Integer dia, @Param("anio") Integer anio, @Param("empleado") Usuario empleado);
	
	
	@Query("SELECT c FROM Turno c WHERE c.empleado = :empleado")
	public List<Turno> buscarPorEmpleado(@Param("empleado") Usuario empleado);
}
