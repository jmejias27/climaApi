package com.clima.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clima.entidad.ClimaLocHistorial;

public interface ClimaLocHistorialDAO extends JpaRepository<ClimaLocHistorial, Long> {

}
