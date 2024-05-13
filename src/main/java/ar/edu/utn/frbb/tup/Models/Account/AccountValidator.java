package ar.edu.utn.frbb.tup.Models.Account;

import ar.edu.utn.frbb.tup.Exceptions.Account.AccountInsuficientFoundsException;

public class AccountValidator {

    private Account account;

    private AccountValidator(Account account) {
        this.account = account;
    }

    public static AccountValidator validate(Account account) {
        return new AccountValidator(account);
    }

    public void hasEnoghBalance(float amount) throws AccountInsuficientFoundsException {
        if (this.account.getBalance() - amount < 0) {
            throw new AccountInsuficientFoundsException("La cuenta tiene insuficiente saldo...");
        }
    }
}
