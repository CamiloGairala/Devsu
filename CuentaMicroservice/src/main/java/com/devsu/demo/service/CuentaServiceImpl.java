package com.devsu.demo.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.devsu.demo.exception.IdNoEncontradaException;
import com.devsu.demo.model.Cuenta;
import com.devsu.demo.model.Movimiento;
import com.devsu.demo.repository.CuentaRepository;

@Service("cuentaService")
public class CuentaServiceImpl implements CuentaService{
	
	@Autowired
	CuentaRepository cuentaRepository;
	
	private static final Logger log = LogManager.getLogger(CuentaServiceImpl.class);

	public List<Cuenta> getAllCuentas() {
		return cuentaRepository.findAll();
	}

	public Cuenta getCuentaById(Long idCuenta) {
		return cuentaRepository.findById(idCuenta).orElse(null);
	}

	@Transactional
	public Cuenta saveCuenta(Cuenta cuenta) {
		try {
			return cuentaRepository.save(cuenta);
		}catch(DataIntegrityViolationException e) {
			log.error("Se intentó crear una cuenta pero no se encontró el cliente");
			String errorDetail = "Id de cliente: "+cuenta.getIdCliente();
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontró ningún cliente con el id: " + cuenta.getIdCliente());
		}
	}

	@Transactional
	public void deleteCuenta(Long idCuenta) {
		try {
			cuentaRepository.deleteById(idCuenta);
		} catch(EmptyResultDataAccessException e) {
			log.error("Se intentó eliminar una cuenta inexistente");
			String errorDetail = "Número de cuenta: "+idCuenta;
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontró ninguna cuenta con el número: " + idCuenta);
		}
		
	}

	@Override
	@Transactional
	//Me pareció que era mejor poner un mismo paso, en lugar de llamar 2 veces, una para verificar y una para actualizar
	public Boolean verificarYActualizarSaldo(Movimiento movimiento) {
		Cuenta cuenta = cuentaRepository.findById(movimiento.getNumeroCuenta()).orElse(null);
		
		if(cuenta!=null) {
			
			//Los saco a variables para hacer más legible el código, en lugar de llamar repetidas veces a los getters
			BigDecimal saldo = cuenta.getSaldoActual();
			BigDecimal imp = movimiento.getValor();
			
			if(imp.compareTo(BigDecimal.ZERO) >= 0) {
				saldo = saldo.add(imp);
				cuenta.setSaldoActual(saldo);
				cuentaRepository.save(cuenta);
				movimiento.setSaldo(saldo);
				return true;
			}
			else {
				//comparo contra el valor absoluto para ver si el número, que ya se que es negativo, es más que el saldo que se tiene
				if(saldo.compareTo(imp.abs())>=0) {
					saldo = saldo.add(imp);
					cuenta.setSaldoActual(saldo);
					cuentaRepository.save(cuenta);
					movimiento.setSaldo(saldo);
					return true;
				}
				else {
					log.error("Se intentó realizar un movimiento negativo superior al saldo disponible");
					String errorDetail = "Numero de cuenta: "+cuenta.getNumeroCuenta();
					errorDetail+=" - Saldo disponible: " + cuenta.getSaldoActual();
					errorDetail+=" - Movimiento: " + imp;
					errorDetail+=" - Fecha del intento: " + new Date();
					log.error(errorDetail);
					return false;
				}
			}
		}
		log.error("Se intentó realizar un movimiento pero no se encontró la cuenta");
		String errorDetail = "Numero de cuenta: "+movimiento.getNumeroCuenta();
		errorDetail+=" - Fecha del intento: " + new Date();
		log.error(errorDetail);
		throw new IdNoEncontradaException("No se encontró ninguna cuenta con el número: " + movimiento.getNumeroCuenta());
	}

	@Override
	public Collection<Cuenta> getCuentasByCliente(Long idCliente) {
		return cuentaRepository.findByIdCliente(idCliente);
	}

	@Transactional
	@Override
	public Cuenta updateCuenta(Cuenta cuenta, Long numeroCuenta) {
		if(numeroCuenta != null) {
			Cuenta c = cuentaRepository.findById(numeroCuenta).orElse(null);
			if(c!=null) {
				c.updateCuenta(cuenta);
				c = cuentaRepository.save(c);
				return c;
			}
			else {
				log.error("Se intentó realizar una modificación en una cuenta pero no se encontró la cuenta");
				String errorDetail = "Numero de cuenta: "+numeroCuenta;
				errorDetail+=" - Fecha del intento: " + new Date();
				log.error(errorDetail);
				throw new IdNoEncontradaException("No se encontró ninguna cuenta con el número: " +numeroCuenta);
			}
		}
		//No debería llegar nunca aquí ya que se recibe por el endpoint, pero por las dudas lo agrego
		throw new IdNoEncontradaException("No se envió un número de cuenta");
	}

}
