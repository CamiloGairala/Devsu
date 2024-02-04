package com.devsu.demo;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devsu.demo.model.Cliente;
import com.devsu.demo.service.ClienteService;

@SpringBootTest(classes = ClienteMicroserviceApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ClienteTest {
	
	@Autowired
	ClienteService clienteService;

    @Test
    public void testCreateCliente() {
        Cliente c = new Cliente();
        c.setContrasena("1234");
        c.setDireccion("Calle falsa 123");
        c.setEdad(45);
        c.setEstado(true);
        c.setGenero("Masculino");
        c.setNombre("Juan Pérez");
        c.setTelefono("+5412341234");
        
        Cliente clienteCreado = clienteService.saveCliente(c);
        
        assertNotNull(clienteCreado);
        assertNotNull(clienteCreado.getClienteId());
        assertNotNull(clienteCreado.getIdentificacion());
    }
    
    @Test
    public void testUpdateCliente() {
        Cliente clienteOld = new Cliente();
        clienteOld.setContrasena("1234");
        clienteOld.setDireccion("Calle falsa 123");
        clienteOld.setEdad(45);
        clienteOld.setEstado(true);
        clienteOld.setGenero("Masculino");
        clienteOld.setNombre("Juan Pérez");
        clienteOld.setTelefono("+5412341234");
        clienteService.saveCliente(clienteOld);
        
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setContrasena("nuevaPassword");
        nuevoCliente.setTelefono("11111111");
        
        nuevoCliente = clienteService.updateCliente(nuevoCliente,clienteOld.getClienteId());
        
        assertEquals("nuevaPassword", nuevoCliente.getContrasena());
        assertEquals("11111111", nuevoCliente.getTelefono());
        assertEquals(clienteOld.getClienteId(),nuevoCliente.getClienteId());
    }
    
    @Test
    public void testReadCliente() {
        Cliente cliente = new Cliente();
        cliente.setContrasena("1234");
        cliente.setDireccion("Calle falsa 123");
        cliente.setEdad(45);
        cliente.setEstado(true);
        cliente.setGenero("Masculino");
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("+5412341234");
        clienteService.saveCliente(cliente);
        
        Long idCliente = cliente.getClienteId();
        
        Cliente clienteTest = clienteService.getClienteById(idCliente);
        
        assertNotNull(clienteTest);
    }
    
    @Test
    public void testReadClientes() {
    	Cliente cliente = new Cliente();
        cliente.setContrasena("1234");
        cliente.setDireccion("Calle falsa 123");
        cliente.setEdad(45);
        cliente.setEstado(true);
        cliente.setGenero("Masculino");
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("+5412341234");
        clienteService.saveCliente(cliente);
        
        Collection<Cliente> clientes = clienteService.getAllClientes();
        
        assertNotNull(clientes);
    }
    
    @Test
    public void testDeleteCliente() {
    	Cliente cliente = new Cliente();
        cliente.setContrasena("1234");
        cliente.setDireccion("Calle falsa 123");
        cliente.setEdad(45);
        cliente.setEstado(true);
        cliente.setGenero("Masculino");
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("+5412341234");
        clienteService.saveCliente(cliente);
        
        Long idCliente = cliente.getClienteId();
        
        clienteService.deleteCliente(idCliente);
        
        Cliente clienteTest = clienteService.getClienteById(idCliente);
        
        assertNull(clienteTest);
    }

}
