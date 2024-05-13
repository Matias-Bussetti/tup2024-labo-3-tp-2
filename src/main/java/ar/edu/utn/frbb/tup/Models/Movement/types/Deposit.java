package ar.edu.utn.frbb.tup.Models.Movement.types;

import ar.edu.utn.frbb.tup.Models.Movement.Movement;

public class Deposit extends Movement {
    public Deposit(float amount) {
        super(amount);
    }

    @Override
    public String toString() {
        return "(Deposito) monto: " + this.amount;
    }
}
