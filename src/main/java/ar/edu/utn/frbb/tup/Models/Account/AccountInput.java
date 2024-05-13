package ar.edu.utn.frbb.tup.Models.Account;

import java.io.Console;

import ar.edu.utn.frbb.tup.Exceptions.Client.ClientHasNoAccountsError;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Models.Client.Client;
import ar.edu.utn.frbb.tup.Models.Client.ClientInput;
import ar.edu.utn.frbb.tup.Models.Client.ClientValidator;
import ar.edu.utn.frbb.tup.Utils.Input;

public class AccountInput extends Input {

    public static Account choseAccount(Banco banco) throws ClientHasNoAccountsError {

        Client client = ClientInput.choseClient(banco);

        ClientValidator.validate(client).hasAccounts();

        for (int i = 0; i < client.getAccounts().size(); i++) {
            Account account = client.getAccounts().get(i);
            printLn(i + ":" + account.getId() + " " + account.getType());
        }

        print("Elegir una Cuenta: ");
        return client.getAccounts().get(Integer.parseInt(readLn()));
    }

    public static String type() {
        print("Tipo: ");
        String type = readLn();
        return type;
    }
}
