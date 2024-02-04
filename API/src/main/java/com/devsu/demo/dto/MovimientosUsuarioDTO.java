package com.devsu.demo.dto;

import java.math.BigDecimal;

import com.devsu.demo.model.Cliente;
import com.devsu.demo.model.Cuenta;
import com.devsu.demo.model.Movimiento;

public class MovimientosUsuarioDTO {
	
	private String fecha;
	private String cliente;
	private Long numeroCuenta;
	private String tipo;
	private BigDecimal saldoInicial;
	private Boolean estado;
	private BigDecimal movimiento;
	private BigDecimal saldoDisponible;
	
	public MovimientosUsuarioDTO() {
		super();
	}

	public MovimientosUsuarioDTO(Cliente cliente,Cuenta cuenta,Movimiento movimiento) {
		
		/*
		 * No me quedaba 100% claro si querían siempre repetir saldo inicial y esas cosas en cada una, o que "saldo inicial" fuera
		 * en realidad el saldo previo al movimiento (y no el inicial). Por eso lo hice así
		*/
		
		this.fecha = movimiento.getFechaFormateada();
		this.cliente = cliente.getNombre();
		this.numeroCuenta = cuenta.getNumeroCuenta();
		this.tipo = cuenta.getTipoCuenta();
		this.saldoInicial = cuenta.getSaldoInicial();
		this.estado = cuenta.getEstado();
		this.movimiento = movimiento.getValor();
		this.saldoDisponible = movimiento.getSaldo();
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public BigDecimal getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(BigDecimal movimiento) {
		this.movimiento = movimiento;
	}
	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}
	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}
	
	

}
