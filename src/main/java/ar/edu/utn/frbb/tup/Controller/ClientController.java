package ar.edu.utn.frbb.tup.Controller;

import ar.edu.utn.frbb.tup.Models.Account.Account;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Models.Client.Client;
import ar.edu.utn.frbb.tup.Models.Client.ClientInput;
import ar.edu.utn.frbb.tup.Utils.Console;

public class ClientController {
    public static void printClients(Banco banco) {
        Console.clearScreen();

        for (Client client : banco.getClients()) {
            Console.outLn(client.toString());
        }
    }

    public static void printClientAccounts(Banco banco) {
        Console.clearScreen();
        Client client = ClientInput.choseClient(banco);
        for (Account account : client.getAccounts()) {
            Console.outLn(account.toString());
        }
    }

    public static void storeClient(Banco banco) {

        Console.clearScreen();

        int id = Banco.getNextId();

        String name = ClientInput.name();
        String lastName = ClientInput.lastName();
        String phoneNumber = ClientInput.phoneNumber();
        String address = ClientInput.address();

        banco.addClient(new Client(id, name, lastName, address, phoneNumber));
        Console.clearScreen();
    }

    public static void updateClient(Banco banco) {
        Client client = ClientInput.choseClient(banco);
        Console.clearScreen();

        client.setName(ClientInput.name());
        client.setLastName(ClientInput.lastName());
        client.setAddress(ClientInput.address());
        client.setPhoneNumber(ClientInput.phoneNumber());

        Console.clearScreen();
    }

    public static void deleteClient(Banco banco) {
        Console.clearScreen();

        Client client = ClientInput.choseClient(banco);

        banco.getClients().remove(client);
        Console.clearScreen();
    }
}
