package com.devsu.demo.service;

import java.util.Collection;
import java.util.List;

import com.devsu.demo.model.Cuenta;
import com.devsu.demo.model.Movimiento;


public interface CuentaService {
	
	public List<Cuenta> getAllCuentas();
	public Cuenta getCuentaById(Long idCuenta);
	public Cuenta saveCuenta(Cuenta cuenta);
	public void deleteCuenta(Long idCuenta);
	
	public Boolean verificarYActualizarSaldo(Movimiento movimiento);
	
	public Collection<Cuenta> getCuentasByCliente(Long idCliente);
	public Cuenta updateCuenta(Cuenta cuenta, Long numeroCuenta);

}
