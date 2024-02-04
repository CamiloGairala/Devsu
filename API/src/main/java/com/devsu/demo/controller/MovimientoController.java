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

import com.devsu.demo.model.Movimiento;
import com.devsu.demo.service.MovimientoService;

@Controller
@RestController
@Validated
@RequestMapping("/movimientos")
public class MovimientoController {
	@Autowired
    private MovimientoService movimientoService;
	
	@GetMapping
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    public Movimiento getMovimientoById(@PathVariable Long id) {
    	return movimientoService.getMovimientoById(id);
    }

    @PostMapping
    public Movimiento saveMovimiento(@Valid @RequestBody Movimiento movimiento, Errors errors) {
        return movimientoService.saveMovimiento(movimiento);
    }
    
    @PutMapping("/{id}")
    public Movimiento updateMovimiento(@PathVariable Long id, @Valid @RequestBody Movimiento movimiento, Errors errors) {
        return movimientoService.updateMovimiento(movimiento,id);
    }
    
    @PatchMapping("/{id}")
    public Movimiento updatePartialMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento, Errors errors) {
        return movimientoService.updateMovimiento(movimiento,id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimiento(@PathVariable Long id) {
    	movimientoService.deleteMovimiento(id);
    }

}
