package com.devsu.demo.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.demo.model.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	Collection<Movimiento> findByNumeroCuentaAndFechaBetweenOrderByFechaDesc(
	        Long numeroCuenta, Date fechaDesde, Date fechaHasta
	    );

}
