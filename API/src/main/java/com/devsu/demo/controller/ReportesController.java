package com.devsu.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.demo.converter.FechaConverter;
import com.devsu.demo.dto.MovimientosUsuarioDTO;
import com.devsu.demo.exception.IdNoEncontradaException;
import com.devsu.demo.model.Cliente;
import com.devsu.demo.model.Cuenta;
import com.devsu.demo.model.Movimiento;
import com.devsu.demo.service.ClienteService;
import com.devsu.demo.service.CuentaService;
import com.devsu.demo.service.MovimientoService;

@RestController
@RequestMapping("/reportes")
public class ReportesController {
	
	@Autowired
	ClienteService clienteService;
	@Autowired
	CuentaService cuentaService;
	@Autowired
	MovimientoService movimientoService;
	
	/*
	 * Hice que recibiera el rango por separado porque me pareció más lógico
	 * Si hubiera que recibirlo si o si entero, definiría un formato obligatorio, ej: dd/mm/yyyy - dd/mm/yyyy
	 * y una vez recibido y verificado extraer cada fecha y guardarla en variables.
	 * 
	 * A su vez, respecto al output, No me quedaba 100% claro si querían siempre repetir saldo inicial y esas cosas en cada una, o que "saldo inicial" fuera
	 * en realidad el saldo previo al movimiento (y no el inicial).
	 */
	@GetMapping
	public List<Map<String, Object>> getEstadoCuenta(@RequestParam("fechaDesde") String fechaDesde, @RequestParam("fechaHasta") String fechaHasta, 
			@RequestParam("cliente") Long clienteId) {
		
		Date fDesde = FechaConverter.convertToDate(fechaDesde);
		Date fHasta= FechaConverter.convertToDate(fechaHasta);
		
		Cliente cliente = clienteService.getClienteById(clienteId);
		List<Map<String, Object>> report = new ArrayList<>();
		
		if(cliente!=null) {
			
			//Asumí que iban a estar ordenados por fecha, agrupados por cuenta. Si no fuera el caso, simplemente los reordenaría y demás
			Collection<Cuenta> cuentas = cuentaService.getCuentasByCliente(clienteId);
			if(cuentas!=null && !cuentas.isEmpty()) {
				for(Cuenta c : cuentas) {
					Collection<Movimiento> movimientos = movimientoService.getAllMovimientosByCuentaEntreFechas(c.getNumeroCuenta(),fDesde,fHasta);
					
					for(Movimiento m : movimientos) {
						MovimientosUsuarioDTO r = new MovimientosUsuarioDTO(cliente,c,m);
						Map<String, Object> entry = new LinkedHashMap<>();
				        entry.put("Fecha", r.getFecha());
				        entry.put("Cliente", r.getCliente());
				        entry.put("Numero Cuenta", r.getNumeroCuenta());
				        entry.put("Tipo", r.getTipo());
				        entry.put("Saldo Inicial", r.getSaldoInicial());
				        entry.put("Estado", r.getEstado());
				        entry.put("Movimiento", r.getMovimiento());
				        entry.put("Saldo Disponible", r.getSaldoDisponible());
				        report.add(entry);
					}
					
				}
			}
			return report;
		}
		throw new IdNoEncontradaException("No se encontró ningún cliente asociado a esa id");
    }
}
