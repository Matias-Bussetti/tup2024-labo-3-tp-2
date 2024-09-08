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

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteValidatorTest {
    // Use a Spy Instead of a Mock: A spy will call real methods unless explicitly
    // stubbed. This ensures that the actual behavior of DtoValidator methods is
    // executed. <- Chat gpt
    @Spy
    private DtoValidator dtoValidator;

    @InjectMocks
    private ClienteValidator clienteValidator;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateSuccess() {
        ClienteDto clienteDto = new ClienteDto();

        clienteDto.setApellido("a").setNombre("a").setDni(12345678).setFechaNacimiento("2001-01-11");
        clienteDto.setBanco("banco").setTipoPersona("J");

        assertDoesNotThrow(() -> clienteValidator.validate(clienteDto));
    }

    @Test
    public void testValidateFails() {
        ClienteDto clienteDto = new ClienteDto();

        clienteDto.setApellido("a").setNombre("a").setDni(1234545678).setFechaNacimiento("2001-01-11");
        clienteDto.setBanco("banco").setTipoPersona("s");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
    }

}
