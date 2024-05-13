package ar.edu.utn.frbb.tup.Models.Movement;

import ar.edu.utn.frbb.tup.Utils.Input;

public class MovementInput extends Input {
    public static float amount() {
        print("Monto: ");
        return Float.parseFloat(readLn());
    }
}
