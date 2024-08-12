package ar.edu.utn.frbb.tup.controller.validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoPersona;

@Component
public class DtoValidator {

    public void stringIsTipoMoneda(String string) {
        if (!("DOLARES".equals(string.toUpperCase()) || "PESOS".equals(string.toUpperCase()))) {
            throw new IllegalArgumentException("El formato de la moneda no es correcta");
        }
    }

    public void stringIsTipoCuenta(String string) {
        if (!(TipoCuenta.isValid(string))) {
            throw new IllegalArgumentException("El formato de la cuenta no es correcta");
        }
    }

    public void longIsValid(long number) {
        try {
            Long.parseLong(String.valueOf(number));
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato del long no es valido");
        }
    }

    public void numberIsBiggerThan(double number, int than) {
        if (!(number > than)) {
            throw new IllegalArgumentException("El número (" + number + ") no es mayor a " + than);
        }
    }

    public void longIsDistinctThanLong(long a, long b) {
        if (a == b) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }
    }

    public void stringIsTipoPersona(String string) {
        if (!(TipoPersona.PERSONA_FISICA.getDescripcion().equals(string)
                || TipoPersona.PERSONA_JURIDICA.getDescripcion().equals(string))) {
            throw new IllegalArgumentException("El tipo de persona no es correcto");
        }
    }

    public void stringIsDateTime(String string) {
        try {
            LocalDate.parse(string);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
    }

    // Validador de Expresión regunlar
    private static void validateStringWithRegEx(String input, String regex, String errorMessage) {
        // Compila el patrón de la expresión regular
        Pattern pattern = Pattern.compile(regex);
        // Crea un Matcher a partir del patrón y de la cadena de entrada
        Matcher matcher = pattern.matcher(input);

        // Si no coincide, lanza una excepción con el mensaje de error
        if (!matcher.matches()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void dniIsValid(long dni) {
        validateStringWithRegEx(String.valueOf(dni), "^(\\d{1,2}\\.?\\d{3}\\.?\\d{3}|\\d{7,8})$",
                "El formato del dni no es valido");
    }

}
