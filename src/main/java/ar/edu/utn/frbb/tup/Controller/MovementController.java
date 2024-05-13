package ar.edu.utn.frbb.tup.Controller;

import ar.edu.utn.frbb.tup.Models.Account.Account;
import ar.edu.utn.frbb.tup.Models.Account.AccountInput;
import ar.edu.utn.frbb.tup.Models.Account.AccountValidator;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Models.Movement.Movement;
import ar.edu.utn.frbb.tup.Models.Movement.MovementInput;
import ar.edu.utn.frbb.tup.Models.Movement.types.Deposit;
import ar.edu.utn.frbb.tup.Models.Movement.types.Transfer;
import ar.edu.utn.frbb.tup.Models.Movement.types.Withdraw;
import ar.edu.utn.frbb.tup.Utils.Console;

public class MovementController {
    public static void printClientAcountMovements(Banco banco) throws Exception {
        Account account = AccountInput.choseAccount(banco);

        for (Movement movement : account.getMovements()) {
            Console.outLn(movement.toString());
        }
    }

    public static void makeDeposit(Banco banco) throws Exception {
        Account account = AccountInput.choseAccount(banco);

        float amount = MovementInput.amount();

        Deposit deposit = new Deposit(amount);

        account.addMovement(deposit);
        account.setBalance(account.getBalance() + amount);
    }

    public static void makeWithdraw(Banco banco) throws Exception {
        Account account = AccountInput.choseAccount(banco);

        float amount = MovementInput.amount();

        AccountValidator.validate(account).hasEnoghBalance(amount);

        Withdraw withdraw = new Withdraw(amount);

        account.addMovement(withdraw);
        account.setBalance(account.getBalance() - amount);
    }

    public static void makeTransfer(Banco banco) throws Exception {
        Account originAccount = AccountInput.choseAccount(banco);
        Account destinyAccount = AccountInput.choseAccount(banco);

        float amount = MovementInput.amount();

        AccountValidator.validate(originAccount).hasEnoghBalance(amount);

        originAccount.addMovement(new Transfer(amount, destinyAccount, "origen"));
        originAccount.sumBalance(-amount);

        destinyAccount.addMovement(new Transfer(amount, destinyAccount, "destino"));
        destinyAccount.sumBalance(amount);
    }
}
