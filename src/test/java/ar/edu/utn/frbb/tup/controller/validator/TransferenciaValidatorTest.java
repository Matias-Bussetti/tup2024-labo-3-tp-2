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
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaValidatorTest {
    // Use a Spy Instead of a Mock: A spy will call real methods unless explicitly
    // stubbed. This ensures that the actual behavior of DtoValidator methods is
    // executed. <- Chat gpt
    @Spy
    private DtoValidator dtoValidator;

    @InjectMocks
    private TransferenciaValidator transferenciaValidator;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateSuccess() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaDestino(0).setCuentaOrigen(1).setMoneda("pesos").setMonto(5000);

        assertDoesNotThrow(() -> transferenciaValidator.validate(transferenciaDto));
    }

    @Test
    public void testValidateFails() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaDestino(0).setCuentaOrigen(1).setMoneda("pesos").setMonto(-1);

        assertThrows(IllegalArgumentException.class, () -> transferenciaValidator.validate(transferenciaDto));
    }

}
