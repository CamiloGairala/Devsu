package com.devsu.demo.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.devsu.demo.exception.IdNoEncontradaException;
import com.devsu.demo.model.Cliente;
import com.devsu.demo.repository.ClienteRepository;
import com.devsu.demo.repository.PersonaRepository;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	PersonaRepository personaRepository;
	
	private static final Logger log = LogManager.getLogger(ClienteServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	public Cliente getClienteById(Long idCliente) {
		return clienteRepository.getByClienteId(idCliente);
	}

	@Transactional
	public Cliente saveCliente(Cliente cliente) {
		clienteRepository.save(cliente);
		//Hago esto para forzar a que traiga si o si el clienteId, que se genera en la base
		entityManager.refresh(cliente);
		return cliente;
	}

	@Transactional
	public void deleteCliente(Long id) {
		//Asumo que como se piden dos entidades diferentes y que cada una tenga su PK, el cliente sabe la idCliente pero no la de persona
		Cliente c = clienteRepository.getByClienteId(id);
		
		if(c!=null) {
			personaRepository.delete(c);
		}
		else {
			log.error("Se intentó eliminar un cliente inexistente");
			String errorDetail = "Id de cliente: "+id;
			errorDetail+=" - Fecha del intento: " + new Date();
			log.error(errorDetail);
			throw new IdNoEncontradaException("No se encontró ningún cliente con el id: " + id);
			}
	}

	@Transactional
	@Override
	public Cliente updateCliente(Cliente cliente, Long id) {
		if(id != null) {
			Cliente c = clienteRepository.getByClienteId(id);
			if(c!=null) {
				c.updateCliente(cliente);
				clienteRepository.save(c);
				return c;
			}
		}
		return null;
	}

}
