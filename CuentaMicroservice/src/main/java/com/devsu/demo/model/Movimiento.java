package com.devsu.demo.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.devsu.demo.exception.FormatoFechaException;

@Entity
@Table(name = "Movimiento")
public class Movimiento {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento")
	private Long idMovimiento;
	@Column(name = "fecha")
	private Date fecha;
	@Column(name = "tipo_movimiento")
	private String tipoMovimiento;
	@NotNull(message = "Debe Introducirse un monto")
	@Column(name = "valor")
	private BigDecimal valor;
	@Column(name = "saldo")	
	private BigDecimal saldo;
	@NotNull(message = "Debe introducirse un numero de cuenta para asociar el movimiento")
	@Column(name = "numero_cuenta")
	private Long numeroCuenta;
	
	/*
	 * Uso este ya que el formato pedido en el ejercicio difiere del que la API puede recibir por Json (como Date), 
	 * y hago esto para no implementar todo un Deserializer
	 */
	
	@Transient
	@NotBlank(message="Debe introducirse una fecha con formato dd/MM/yyyy")
	private String fechaFormateada;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	} 
	
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Long getIdMovimiento() {
		return idMovimiento;
	}
	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	public void setFechaFormateada(String fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.setFecha(dateFormat.parse(fecha));
            this.fechaFormateada=fecha;
        } catch (ParseException e) {
            throw new FormatoFechaException("El formato de fecha introducida debe ser dd/MM/yyyy");
        }
	}
	
	public String getFechaFormateada() {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 return sdf.format(this.getFecha());
	}
	
	public Movimiento updateMovimiento(Movimiento m) {
		if(m.getFechaFormateada()!=null && !m.getFechaFormateada().equals("")) {
			this.setFechaFormateada(m.getFechaFormateada());
		}
		if(m.getNumeroCuenta()!=null) {
			this.setNumeroCuenta(m.getNumeroCuenta());
		}
		if(m.getSaldo()!=null) {
			this.setSaldo(m.getSaldo());
		}
		if(m.getTipoMovimiento()!=null && !m.getTipoMovimiento().equals("")) {
			this.setTipoMovimiento(m.getTipoMovimiento());
		}
		if(m.getValor()!=null) {
			this.setValor(m.getValor());
		}
		return this;
	}

}
