package com.devsu.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devsu.demo.exception.SaldoInsuficienteException;
import com.devsu.demo.model.Cuenta;
import com.devsu.demo.model.Movimiento;
import com.devsu.demo.service.CuentaService;
import com.devsu.demo.service.MovimientoService;

@SpringBootTest(classes = CuentaMicroserviceApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CuentaMovimientosIntegrationTest {
	
	@Autowired
	CuentaService cuentaService;
	@Autowired
	MovimientoService movimientoService;
	
    private Cuenta getCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setEstado(true);
        cuenta.setSaldoInicial(new BigDecimal(1000));
        cuenta.setTipoCuenta("Cuenta test 1000 saldo inicial");
        /*
         * Para no mezclar los servicios ni crear una falsa base de datos ni ponerle métodos externor al microservicio de Clientes,
         * decidí usar por defecto este valor. Dependiendo del modelo de negocios se podría hacer cualquiera de esas cosas, o poner
         * una tabla intermedia de Clientes x Cuentas
         */
        cuenta.setIdCliente(1L);  
        return cuentaService.saveCuenta(cuenta);
    }

    @Test
    public void testCrearCuentaYMovimientosMultiples() {
    	Cuenta cuenta = this.getCuenta();
    	BigDecimal saldoAProbar = new BigDecimal(100);
    	
    	Movimiento movPositivo = new Movimiento();
    	movPositivo.setFechaFormateada("01/01/2023");
    	movPositivo.setNumeroCuenta(cuenta.getNumeroCuenta());
    	movPositivo.setTipoMovimiento("Depósito");
    	movPositivo.setValor(saldoAProbar);
    	
    	movPositivo = movimientoService.saveMovimiento(movPositivo);
    	
    	cuenta = cuentaService.getCuentaById(cuenta.getNumeroCuenta()); //Para asegurar que la busque nuevamente, con los cambios hechos
    	
    	//uso estos compare porque el big decimal puede tener o no decimales y en base a eso no devolver el mismo valor
    	//Saldo inicial + depósito = saldo que marca el movimiento
    	assertEquals(0,movPositivo.getSaldo().compareTo(cuenta.getSaldoInicial().add(saldoAProbar))); 
    	//Saldo actual = saldoInicial + importe del movimiento
    	assertEquals(0,cuenta.getSaldoActual().compareTo(cuenta.getSaldoInicial().add(movPositivo.getValor())));
    	
    	BigDecimal saldoTemporal = cuenta.getSaldoActual();
    	saldoAProbar = saldoAProbar.negate();
    	
    	Movimiento movNegativo = new Movimiento();
    	movNegativo.setFechaFormateada("01/01/2023");
    	movNegativo.setNumeroCuenta(cuenta.getNumeroCuenta());
    	movNegativo.setTipoMovimiento("Extraccion");
    	movNegativo.setValor(saldoAProbar);
    	
    	movNegativo = movimientoService.saveMovimiento(movNegativo);
    	
    	cuenta = cuentaService.getCuentaById(cuenta.getNumeroCuenta()); //Para asegurar que la busque nuevamente, con los cambios hechos
    	
    	//Saldo previo al movimiento - extraccion = saldo que marca el movimiento
    	assertEquals(0,movNegativo.getSaldo().compareTo(saldoTemporal.add(saldoAProbar)));
    	//Saldo actual = saldo anterior - importe del movimiento
    	assertEquals(0,cuenta.getSaldoActual().compareTo(saldoTemporal.add(movNegativo.getValor())));
    	
    }
    
    @Test
    public void testGenerarMovimientoSuperiorSaldoDisponible() {
    	Cuenta cuenta = this.getCuenta();
    	BigDecimal saldoAProbar = cuenta.getSaldoActual();
    	saldoAProbar = saldoAProbar.subtract(saldoAProbar.multiply(new BigDecimal(2)));
    	saldoAProbar = saldoAProbar.subtract(new BigDecimal(1));
    	
    	Movimiento movSuperaBalance = new Movimiento();
    	movSuperaBalance.setFechaFormateada("01/01/2023");
    	movSuperaBalance.setNumeroCuenta(cuenta.getNumeroCuenta());
    	movSuperaBalance.setTipoMovimiento("Extraccion");
    	movSuperaBalance.setValor(saldoAProbar);
    	
    	Movimiento movTest = null;
    	try {
    		movTest = movimientoService.saveMovimiento(movSuperaBalance);
    	} catch(SaldoInsuficienteException e) {
    		assertNotNull(e);
    	}
    	assertNull(movTest);
    	cuenta = cuentaService.getCuentaById(cuenta.getNumeroCuenta()); //Para asegurar que la busque nuevamente, con los cambios hechos
    	assertEquals(0,cuenta.getSaldoActual().compareTo(cuenta.getSaldoInicial()));
    	
    }

}
