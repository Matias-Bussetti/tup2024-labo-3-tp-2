package ar.edu.utn.frbb.tup.Models.Movement.types;

import ar.edu.utn.frbb.tup.Models.Account.Account;
import ar.edu.utn.frbb.tup.Models.Movement.Movement;

public class Transfer extends Movement {
    private Account account;
    private String type;

    public Transfer(float amount, Account account, String type) {
        super(amount);
        this.account = account;
        this.type = type;
    }

    public Account getaccount() {
        return account;
    }

    public void setaccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "(Transferencia " + this.type + ") monto: " + this.amount
                + "\n Cuenta destino: " + this.account.getId();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
