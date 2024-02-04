package com.devsu.demo.service;

import java.util.List;

import com.devsu.demo.model.Cliente;

public interface ClienteService {
	
	public List<Cliente> getAllClientes();
	public Cliente getClienteById(Long idCliente);
	public Cliente saveCliente(Cliente cliente);
	public void deleteCliente(Long idCliente);
	public Cliente updateCliente(Cliente cliente, Long id);

}
