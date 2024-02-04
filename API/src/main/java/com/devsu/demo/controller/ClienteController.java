package com.devsu.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.demo.model.Cliente;
import com.devsu.demo.service.ClienteService;

@Controller
@RestController
@Validated
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
    private ClienteService clienteService;
	
	@GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping
    public Cliente saveCliente(@Valid @RequestBody Cliente cliente,Errors error) {
        return clienteService.saveCliente(cliente);
    }
    
    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente,Errors error) {
        return clienteService.updateCliente(cliente,id);
    }
    
    @PatchMapping("/{id}")
    public Cliente updatePartialCliente(@PathVariable Long id, @RequestBody Cliente cliente,Errors error) {
        return clienteService.updateCliente(cliente,id);
    }

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }

}
