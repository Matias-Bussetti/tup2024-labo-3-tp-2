package ar.edu.utn.frbb.tup.Models.Movement.types;

import ar.edu.utn.frbb.tup.Models.Movement.Movement;

public class Withdraw extends Movement {
    public Withdraw(float amount) {
        super(amount);
    }

    @Override
    public String toString() {
        return "(Retiro) monto: " + this.amount;
    }
}
