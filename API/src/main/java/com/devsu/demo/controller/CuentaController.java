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

import com.devsu.demo.model.Cuenta;
import com.devsu.demo.service.CuentaService;

@Controller
@RestController
@Validated
@RequestMapping("/cuentas")
public class CuentaController {
	
	@Autowired
    private CuentaService cuentaService;
	
	@GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public Cuenta getCuentaById(@PathVariable Long id) {
    	return cuentaService.getCuentaById(id);
    }

    @PostMapping
    public Cuenta saveCuenta(@Valid @RequestBody Cuenta cuenta, Errors errors) {
        return cuentaService.saveCuenta(cuenta);
    }
    
    @PutMapping("/{id}")
    public Cuenta updateCuenta(@PathVariable Long id,@Valid @RequestBody Cuenta cuenta, Errors errors) {
    	return cuentaService.updateCuenta(cuenta,id);
    }
    
    @PatchMapping("/{id}")
    public Cuenta updatePartialCuenta(@PathVariable Long id,@RequestBody Cuenta cuenta, Errors errors) {
        return cuentaService.updateCuenta(cuenta,id);
    }

    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) {
    	cuentaService.deleteCuenta(id);
    }
}
