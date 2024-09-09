package ar.edu.utn.frbb.tup.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.CuentaTransferencias;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteDoesNotExistException;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaDoesNotExistException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoMovimiento;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

//Anotacion para usar mockito
@ExtendWith(MockitoExtension.class)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {

    @Mock // Mock de ClienteService (repositorio)
    private CuentaDao cuentaDao;
    @Mock // Mock de ClienteService (repositorio)
    private ClienteDao clienteDao;

    @Mock
    private ServiceValidator serviceValidator;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    @DisplayName("Ejercicios del TP de testing")

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDarDeAltaCuentaSuccess() throws TipoCuentaAlreadyExistsException, TipoCuentaNotSupportedException,
            ClienteYaTieneTipoCuentaException, CuentaAlreadyExistsException, ClienteDoesNotExistException {
        CuentaDto cuentaDto = new CuentaDto().setMoneda("PESOS").setTipoCuenta("CA$");

        Cliente cliente = new Cliente();
        cliente.setNombre("matias").setApellido("bsstt").setDni(4326).setFechaNacimiento(LocalDate.of(2002, 1, 1));
        cliente.setBanco("banco").setTipoPersona("F");

        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto, 4326);

        verify(cuentaDao, times(1)).save(cuenta);
    }

    @Test
    public void testFindSuccess() {
        Cuenta cuenta = new Cuenta();

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(cuenta);
        when(cuentaDao.find(cuenta.getNumeroCuenta(), true)).thenReturn(cuenta);

        assertDoesNotThrow(() -> cuentaService.find(cuenta.getNumeroCuenta()));
        assertDoesNotThrow(() -> cuentaService.find(cuenta.getNumeroCuenta(), true));
    }

    @Test
    public void testRegistrarMovimientoSaliente() {
        Cuenta cuenta = new Cuenta();
        TransferenciaDto transferenciaDto = new TransferenciaDto();

        transferenciaDto.setCuentaDestino(cuenta.getNumeroCuenta()).setCuentaDestino(123456789).setMoneda("pesos")
                .setMonto(1000);

        cuentaService.registrarMovimientoSaliente(cuenta, transferenciaDto);

        assertEquals(1, cuenta.getMovimientos().size());
    }

    @Test
    public void testRegistrarMovimientoEntrante() {
        Cuenta cuenta = new Cuenta();
        TransferenciaDto transferenciaDto = new TransferenciaDto();

        transferenciaDto.setCuentaDestino(cuenta.getNumeroCuenta()).setCuentaDestino(123456789).setMoneda("pesos")
                .setMonto(1000);

        cuentaService.registrarMovimientoEntrante(cuenta, transferenciaDto);

        assertEquals(1, cuenta.getMovimientos().size());
    }

    @Test
    public void testObtenerTransaccionesDeCuenta() throws CuentaDoesNotExistException {
        Cuenta cuenta = new Cuenta();
        TransferenciaDto transferenciaDto = new TransferenciaDto();

        transferenciaDto.setCuentaDestino(cuenta.getNumeroCuenta()).setCuentaDestino(123456789).setMoneda("pesos")
                .setMonto(1000);

        cuentaService.registrarMovimientoEntrante(cuenta, transferenciaDto);
        cuentaService.registrarMovimientoEntrante(cuenta, transferenciaDto);

        when(cuentaDao.find(cuenta.getNumeroCuenta(), true)).thenReturn(cuenta);

        CuentaTransferencias cuentaTransferencias = cuentaService
                .obtenerTransaccionesDeCuenta(cuenta.getNumeroCuenta());

        assertEquals(2, cuentaTransferencias.getMovimientos().size());
    }

}
