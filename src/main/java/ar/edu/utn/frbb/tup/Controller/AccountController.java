package ar.edu.utn.frbb.tup.Controller;

import ar.edu.utn.frbb.tup.Models.Account.Account;
import ar.edu.utn.frbb.tup.Models.Account.AccountInput;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Models.Client.Client;
import ar.edu.utn.frbb.tup.Models.Client.ClientInput;
import ar.edu.utn.frbb.tup.Utils.Console;

public class AccountController {

    public static void addAccount(Banco banco) {
        Console.clearScreen();

        Client client = ClientInput.choseClient(banco);

        int id = Banco.getNextId();

        String type = AccountInput.type();

        client.addAccount(new Account(id, type));

        Console.clearScreen();
    }

    public static void accountBalance(Banco banco) throws Exception {
        Console.clearScreen();

        Account account = AccountInput.choseAccount(banco);

        Console.clearScreen();

        Console.outLn("Balance " + String.format("%.0f", account.getBalance()));
    }
}
