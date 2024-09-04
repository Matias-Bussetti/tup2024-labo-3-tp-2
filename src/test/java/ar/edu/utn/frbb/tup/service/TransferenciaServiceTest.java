package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoEstadoDeTransferencia;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoTransferenciaResultado;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientoDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaServiceTest {

    @Mock // Mock de ClienteService (repositorio)
    private CuentaDao cuentaDao;

    @Mock // Mock de ClienteService (repositorio)
    private ClienteDao clienteDao;

    @Mock // Mock de ClienteService (repositorio)
    private MovimientoDao movimientoDao;

    @Mock
    private ServiceValidator serviceValidator;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecibirTransferenciaSuccess() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        Cuenta cuentaB = new Cuenta();
        cuentaB.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        // when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(cuentaB.getNumeroCuenta())).thenReturn(cuentaB);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaDestino(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        assertEquals(TipoEstadoDeTransferencia.EXITOSA,
                transferenciaService.recibirTransferencia(transferenciaDto).getEstado());
        // assertEquals(1, cuentaB.getMovimientos().size());
    }

    @Test
    public void testRecibirTransferenciaSuccess() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        Cuenta cuentaB = new Cuenta();
        cuentaB.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        // when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(cuentaB.getNumeroCuenta())).thenReturn(cuentaB);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaDestino(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        assertEquals(TipoEstadoDeTransferencia.EXITOSA,
                transferenciaService.recibirTransferencia(transferenciaDto).getEstado());
        // assertEquals(1, cuentaB.getMovimientos().size());
    }

}
