package com.devsu.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Cuenta")
public class Cuenta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numero_cuenta")
	private Long numeroCuenta;
	@NotBlank(message = "Debe introducirse un tipo de cuenta")
	@Column(name = "tipo_cuenta")
	private String tipoCuenta;
	@Column(name = "saldo_inicial")
	@NotNull(message="Debe introducirse un saldo inicial")
	private BigDecimal saldoInicial;
	@NotNull(message = "Debe introducirse un estado")
	@Column(name = "estado")
	private Boolean estado;
	@NotNull(message = "Debe introducirse una identificación para el cliente al cuál se asociará la cuenta")
	@Column(name = "id_cliente")
	private Long idCliente;
	
	@Column(name = "saldoActual")
	private BigDecimal saldoActual;
	
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public BigDecimal getSaldoInicial() {
		if(saldoInicial==null) {
			this.saldoInicial=new BigDecimal(0);
		}
		return saldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		if(this.saldoActual ==null) {
			this.saldoActual=saldoInicial;
		}
		this.saldoInicial = saldoInicial;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Cuenta updateCuenta(Cuenta c) {
		if(c.getTipoCuenta()!=null && !c.getTipoCuenta().equals("")) {
			this.setTipoCuenta(c.getTipoCuenta());
		}
		if(c.getSaldoInicial()!=null) {
			this.setSaldoInicial(c.getSaldoInicial());
		}
		if(c.getEstado()!=null) {
			this.setEstado(c.getEstado());
		}
		if(c.getIdCliente()!=null) {
			this.setIdCliente(c.getIdCliente());
		}
		return this;
	}
	public BigDecimal getSaldoActual() {
		return this.saldoActual;
	}
	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}
	
}
