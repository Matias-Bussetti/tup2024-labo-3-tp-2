package ar.edu.utn.frbb.tup.controller.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.service.TransferenciaService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DtoValidatorTest {

    @InjectMocks
    private DtoValidator dtoValidator;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStringIsTipoCuentaSuccess() {
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoCuenta("CA$"));
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoCuenta("CC$"));
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoCuenta("CAU$S"));
    }

    @Test
    public void testStringIsTipoCuentaFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsTipoCuenta("pesoa"));
    }

    @Test
    public void testStringIsTipoMonedaSuccess() {
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoMoneda("pesos"));
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoMoneda("dolares"));
    }

    @Test
    public void testStringIsTipoMonedaFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsTipoMoneda("pesoa"));
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsTipoMoneda("dolare"));
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsTipoMoneda(""));
    }

    @Test
    public void testLongIsValidSuccess() {
        assertDoesNotThrow(() -> dtoValidator.longIsValid(13));
    }

    @Test
    public void testLongIsValidFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.longIsValid(Long.parseLong("123x")));
    }

    @Test
    public void testNumberIsBiggerThanMonedaSuccess() {
        assertDoesNotThrow(() -> dtoValidator.numberIsBiggerThan(13, 0));
    }

    @Test
    public void testNumberIsBiggerThanMonedaFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.numberIsBiggerThan(0, 0));
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.numberIsBiggerThan(0, 1));
    }

    @Test
    public void testLongIsDistinctThanLongThanSuccess() {
        assertDoesNotThrow(() -> dtoValidator.longIsDistinctThanLong(13, 0));
    }

    @Test
    public void testLongIsDistinctThanLongThanFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.longIsDistinctThanLong(0, 0));
    }

    @Test
    public void testStringIsTipoPersonaSuccess() {
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoPersona("F"));
        assertDoesNotThrow(() -> dtoValidator.stringIsTipoPersona("J"));
    }

    @Test
    public void testStringIsTipoPersonaFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsTipoPersona("a"));
    }

    @Test
    public void testStringIsDateTimeSuccess() {
        assertDoesNotThrow(() -> dtoValidator.stringIsDateTime("2018-05-05"));
    }

    @Test
    public void testStringIsDateTimeFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.stringIsDateTime("a"));
    }

    @Test
    public void testDniIsValidSuccess() {
        assertDoesNotThrow(() -> dtoValidator.dniIsValid(12345678));
    }

    @Test
    public void testDniIsValidFails() {
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.dniIsValid(Long.valueOf("a")));
        assertThrows(IllegalArgumentException.class, () -> dtoValidator.dniIsValid(987_987_987));
    }

}
