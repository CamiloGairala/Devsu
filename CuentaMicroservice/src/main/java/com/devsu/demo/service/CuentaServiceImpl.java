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
			log.error("Se intent� crear una cuenta pero no se encontr� el cliente");
			String errorDetail = "Id de cliente: "+cuenta.getIdCliente();
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontr� ning�n cliente con el id: " + cuenta.getIdCliente());
		}
	}

	@Transactional
	public void deleteCuenta(Long idCuenta) {
		try {
			cuentaRepository.deleteById(idCuenta);
		} catch(EmptyResultDataAccessException e) {
			log.error("Se intent� eliminar una cuenta inexistente");
			String errorDetail = "N�mero de cuenta: "+idCuenta;
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontr� ninguna cuenta con el n�mero: " + idCuenta);
		}
		
	}

	@Override
	@Transactional
	//Me pareci� que era mejor poner un mismo paso, en lugar de llamar 2 veces, una para verificar y una para actualizar
	public Boolean verificarYActualizarSaldo(Movimiento movimiento) {
		Cuenta cuenta = cuentaRepository.findById(movimiento.getNumeroCuenta()).orElse(null);
		
		if(cuenta!=null) {
			
			//Los saco a variables para hacer m�s legible el c�digo, en lugar de llamar repetidas veces a los getters
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
				//comparo contra el valor absoluto para ver si el n�mero, que ya se que es negativo, es m�s que el saldo que se tiene
				if(saldo.compareTo(imp.abs())>=0) {
					saldo = saldo.add(imp);
					cuenta.setSaldoActual(saldo);
					cuentaRepository.save(cuenta);
					movimiento.setSaldo(saldo);
					return true;
				}
				else {
					log.error("Se intent� realizar un movimiento negativo superior al saldo disponible");
					String errorDetail = "Numero de cuenta: "+cuenta.getNumeroCuenta();
					errorDetail+=" - Saldo disponible: " + cuenta.getSaldoActual();
					errorDetail+=" - Movimiento: " + imp;
					errorDetail+=" - Fecha del intento: " + new Date();
					log.error(errorDetail);
					return false;
				}
			}
		}
		log.error("Se intent� realizar un movimiento pero no se encontr� la cuenta");
		String errorDetail = "Numero de cuenta: "+movimiento.getNumeroCuenta();
		errorDetail+=" - Fecha del intento: " + new Date();
		log.error(errorDetail);
		throw new IdNoEncontradaException("No se encontr� ninguna cuenta con el n�mero: " + movimiento.getNumeroCuenta());
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
				log.error("Se intent� realizar una modificaci�n en una cuenta pero no se encontr� la cuenta");
				String errorDetail = "Numero de cuenta: "+numeroCuenta;
				errorDetail+=" - Fecha del intento: " + new Date();
				log.error(errorDetail);
				throw new IdNoEncontradaException("No se encontr� ninguna cuenta con el n�mero: " +numeroCuenta);
			}
		}
		//No deber�a llegar nunca aqu� ya que se recibe por el endpoint, pero por las dudas lo agrego
		throw new IdNoEncontradaException("No se envi� un n�mero de cuenta");
	}

}
