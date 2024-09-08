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

    @Mock
    private BanelcoService banelcoService;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogicaDeNegocioALaCuentaDestinoCasoPesos100000() {
        Cuenta cuenta = new Cuenta().setBalance(0).setMoneda(TipoMoneda.PESOS);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setMoneda("pesos").setMonto(1_000_000);

        transferenciaService.logicaDeNegocioALaCuentaDestino(cuenta, transferenciaDto);

        assertEquals((1_000_000 * 0.98), cuenta.getBalance());
    }

    @Test
    public void testLogicaDeNegocioALaCuentaDestinoCasoPesosNo100000() {
        Cuenta cuenta = new Cuenta().setBalance(0).setMoneda(TipoMoneda.PESOS);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setMoneda("pesos").setMonto(100_000);

        transferenciaService.logicaDeNegocioALaCuentaDestino(cuenta, transferenciaDto);

        assertEquals((100_000), cuenta.getBalance());
    }

    @Test
    public void testLogicaDeNegocioALaCuentaDestinoCasoDolares5000() {
        Cuenta cuenta = new Cuenta().setBalance(0).setMoneda(TipoMoneda.PESOS);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        transferenciaService.logicaDeNegocioALaCuentaDestino(cuenta, transferenciaDto);

        assertEquals((5000 * 0.95), cuenta.getBalance());
    }

    @Test
    public void testLogicaDeNegocioALaCuentaDestinoCasoDolaresNo5000() {
        Cuenta cuenta = new Cuenta().setBalance(0).setMoneda(TipoMoneda.PESOS);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setMoneda("dolares").setMonto(4999);

        transferenciaService.logicaDeNegocioALaCuentaDestino(cuenta, transferenciaDto);

        assertEquals(4999, cuenta.getBalance());
    }

    @Test
    public void testCuentaOrigenRealizarTransferencia() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        Cuenta cuentaB = new Cuenta();
        cuentaB.setBalance(0).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        // when(cuentaDao.find(cuentaB.getNumeroCuenta())).thenReturn(cuentaB);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        transferenciaService.cuentaOrigenRealizarTransferencia(transferenciaDto);

        assertEquals(0, cuentaA.getBalance());
    }

    @Test
    public void testCuentaDestinoRealizarTransferencia() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        Cuenta cuentaB = new Cuenta();
        cuentaB.setBalance(0).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        // when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(cuentaB.getNumeroCuenta())).thenReturn(cuentaB);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        transferenciaService.cuentaDestinoRealizarTransferencia(transferenciaDto);

        assertEquals(5000 * 0.95, cuentaB.getBalance());
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
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        assertEquals(TipoEstadoDeTransferencia.EXITOSA,
                transferenciaService.recibirTransferencia(transferenciaDto).getEstado());
        // assertEquals(1, cuentaB.getMovimientos().size());
    }

    @Test
    public void testRecibirTransferenciaFails() throws NoAlcanzaException, CantidadNegativaException {

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1).setCuentaDestino(2);
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        when(cuentaDao.find(1, false)).thenReturn(null);

        assertEquals(TipoEstadoDeTransferencia.FALLIDA,
                transferenciaService.recibirTransferencia(transferenciaDto).getEstado());
    }

    @Test
    public void testTransferirCasoMismoBancoSuccess() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        Cuenta cuentaB = new Cuenta();
        cuentaB.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(cuentaB.getNumeroCuenta())).thenReturn(cuentaB);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(cuentaB.getNumeroCuenta());
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        assertEquals(TipoEstadoDeTransferencia.EXITOSA,
                transferenciaService.transferir(transferenciaDto).getEstado());

    }

    @Test
    public void testTransferirCasoMismoBancoFails() throws NoAlcanzaException, CantidadNegativaException {

        when(cuentaDao.find(0)).thenReturn(null);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(0).setCuentaDestino(1);
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        assertEquals(TipoEstadoDeTransferencia.FALLIDA,
                transferenciaService.transferir(transferenciaDto).getEstado());
    }

    @Test
    public void testTransferirCasoBanelcoServiceSuccess() throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(1)).thenReturn(null);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(1);
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        when(banelcoService.transferir(transferenciaDto)).thenReturn(TipoTransferenciaResultado.OK);

        assertEquals(TipoEstadoDeTransferencia.EXITOSA,
                transferenciaService.transferir(transferenciaDto).getEstado());

    }

    @Test
    public void testTransferirCasoBanelcoServiceFails() throws NoAlcanzaException, CantidadNegativaException {

        Cuenta cuentaA = new Cuenta();
        cuentaA.setBalance(5000).setTipoCuenta(TipoCuenta.CA$).setMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.find(cuentaA.getNumeroCuenta())).thenReturn(cuentaA);
        when(cuentaDao.find(1)).thenReturn(null);

        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(cuentaA.getNumeroCuenta()).setCuentaDestino(1);
        transferenciaDto.setMoneda("dolares").setMonto(5000);

        when(banelcoService.transferir(transferenciaDto)).thenReturn(TipoTransferenciaResultado.ERROR);

        assertEquals(TipoEstadoDeTransferencia.FALLIDA,
                transferenciaService.transferir(transferenciaDto).getEstado());
    }

}
