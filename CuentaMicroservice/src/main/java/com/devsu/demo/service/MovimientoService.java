package com.devsu.demo.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.devsu.demo.model.Movimiento;


public interface MovimientoService {
	
	public List<Movimiento> getAllMovimientos();
	public Movimiento getMovimientoById(Long idMovimiento);
	public Movimiento saveMovimiento(Movimiento movimiento);
	public void deleteMovimiento(Long idMovimiento);
	
	public Collection<Movimiento> getAllMovimientosByCuentaEntreFechas(Long numeroCuenta, Date desde, Date hasta);
	public Movimiento updateMovimiento(Movimiento movimiento, Long id);

}
