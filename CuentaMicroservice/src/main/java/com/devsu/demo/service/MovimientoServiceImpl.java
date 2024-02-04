package com.devsu.demo.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.devsu.demo.exception.IdNoEncontradaException;
import com.devsu.demo.exception.SaldoInsuficienteException;
import com.devsu.demo.model.Movimiento;
import com.devsu.demo.repository.MovimientoRepository;

@Service("movimientoService")
public class MovimientoServiceImpl implements MovimientoService{
	
	@Autowired
	MovimientoRepository movimientoRepository;
	@Autowired
	CuentaService cuentaService;
	
	private static final Logger log = LogManager.getLogger(MovimientoServiceImpl.class);

	public List<Movimiento> getAllMovimientos() {
		return movimientoRepository.findAll();
	}

	public Movimiento getMovimientoById(Long idMovimiento) {
		return movimientoRepository.findById(idMovimiento).orElse(null);
	}

	@Transactional
	public Movimiento saveMovimiento(Movimiento movimiento) {
		
		if(cuentaService.verificarYActualizarSaldo(movimiento)) {
			return movimientoRepository.save(movimiento);
		}
		else {
			throw new SaldoInsuficienteException("No posee saldo suficiente para realizar el movimiento");
		}
	}

	@Transactional
	public void deleteMovimiento(Long idMovimiento) {
		try {
			movimientoRepository.deleteById(idMovimiento);
		} catch(EmptyResultDataAccessException e) {
			log.error("Se intentó eliminar un movimiento inexistente");
			String errorDetail = "Id de movimiento: "+idMovimiento;
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontró ningún movimiento con el id: " + idMovimiento);
		}
	}

	@Override
	public Collection<Movimiento> getAllMovimientosByCuentaEntreFechas(Long numeroCuenta, Date desde, Date hasta) {
		return movimientoRepository.findByNumeroCuentaAndFechaBetweenOrderByFechaDesc(numeroCuenta,desde,hasta);
	}

	@Override
	@Transactional
	public Movimiento updateMovimiento(Movimiento movimiento, Long id) {
		if(id != null) {
			Movimiento m = movimientoRepository.findById(id).orElse(null);
			if(m!=null) {
				m.updateMovimiento(movimiento);
				if(cuentaService.verificarYActualizarSaldo(movimiento)) {
					movimientoRepository.save(m);
					return m;
				}
				throw new SaldoInsuficienteException("No posee saldo suficiente para realizar el movimiento");
			}
			log.error("Se intentó modificar un movimiento inexistente");
			String errorDetail = "Id de movimiento: "+id;
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontró ningún movimiento con el id: " + id);
		}
		return null;
	}

}
