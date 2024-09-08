package ar.edu.utn.frbb.tup.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @Mock
    private CuentaValidator cuentaValidator;

    @Mock
    private ServiceValidator serviceValidator;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCuenta() throws Exception, Throwable {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("pesos").setTipoCuenta("CA$");

        when(cuentaService.darDeAltaCuenta(cuentaDto, 0)).thenReturn(new Cuenta(cuentaDto));

        assertEquals(0,
                cuentaController.crearCuenta(cuentaDto, 0).getBalance());
    }
}
