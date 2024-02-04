package com.devsu.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;
	@NotBlank(message = "Debe introducirse una contraseña")
    @Column(name = "contrasena")
    private String contrasena;
	@NotNull(message = "Debe introducirse un estado")
    @Column(name = "estado")
    private Boolean estado;

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

//	public Persona getPersona() {
//		return persona;
//	}
//
//	public void setPersona(Persona persona) {
//		this.persona = persona;
//	}
	
	public Cliente updateCliente(Cliente c) {
		if(c.getContrasena()!=null && !c.getContrasena().equals("")) {
			this.setContrasena(c.getContrasena());
		}
		if(c.getEstado()!=null) {
			this.setEstado(c.getEstado());
		}
		if(c.getDireccion()!=null && !c.getDireccion().equals("")) {
			this.setDireccion(c.getDireccion());
		}
		if(c.getEdad()!=null) {
			this.setEdad(c.getEdad());
		}
		if(c.getGenero()!=null && !c.getGenero().equals("")) {
			this.setGenero(c.getGenero());
		}
		if(c.getNombre()!=null && !c.getNombre().equals("")) {
			this.setNombre(c.getNombre());
		}
		if(c.getTelefono()!=null && !c.getTelefono().equals("")) {
			this.setTelefono(c.getTelefono());
		}
		
		return this;
	}
    
    
}