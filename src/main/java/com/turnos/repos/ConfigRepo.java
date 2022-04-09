package com.turnos.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turnos.entities.Config;

@Repository
public interface ConfigRepo extends JpaRepository<Config, Long> {

}
