package ar.edu.utn.frbb.tup.controller.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaValidatorTest {
    // Use a Spy Instead of a Mock: A spy will call real methods unless explicitly
    // stubbed. This ensures that the actual behavior of DtoValidator methods is
    // executed. <- Chat gpt
    @Spy
    private DtoValidator dtoValidator;

    @InjectMocks
    private CuentaValidator cuentaValidator;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateSuccess() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("pesos").setTipoCuenta("CA$");
        assertDoesNotThrow(() -> cuentaValidator.validate(cuentaDto, 12_132_123));
    }

    // @Test
    public void testValidateFails() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("pesos").setTipoCuenta("CA$");
        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto, 0));
    }

}
