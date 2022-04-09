package com.turnos.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turnos.entities.Horario;
import com.turnos.entities.Usuario;

@Repository
public interface HorarioRepo extends JpaRepository<Horario, Integer> {

	@Query("SELECT DISTINCT c FROM Horario c WHERE c.mes = :mes AND c.dia = :dia AND c.empleado = :empleado AND c.anio = :anio")
	public List<Horario> existeHorario(@Param("mes") Integer mes, @Param("dia") Integer dia,
			@Param("anio") Integer anio, @Param("empleado") Usuario empleado);

	@Query("SELECT DISTINCT c FROM Horario c WHERE c.mes = :mes AND c.dia = :dia AND c.empleado = :empleado AND c.anio = :anio")
	public List<Horario> horariosPorDia(@Param("mes") Integer mes, @Param("dia") Integer dia,
			@Param("anio") Integer anio, @Param("empleado") Usuario empleado);

	@Query("SELECT DISTINCT c FROM Horario c WHERE c.mes = :mes AND c.empleado = :empleado AND c.anio = :anio")
	public List<Horario> horariosPorMes(@Param("mes") Integer mes, @Param("anio") Integer anio,
			@Param("empleado") Usuario empleado);

}
