package ar.edu.utn.frbb.tup.Models.Client;

import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Utils.Input;

public class ClientInput extends Input {
    public static Client choseClient(Banco banco) {
        clearScreen();

        for (int i = 0; i < banco.getClients().size(); i++) {
            Client client = banco.getClients().get(i);
            printLn(i + ":" + client.getName() + " " + client.getLastName());
        }

        print("Elige un Cliente: ");
        return banco.getClients().get(Integer.parseInt(readLn()));
    }

    public static String name() {
        print("Nombre: ");
        String input = readLn();
        return input;
    }

    public static String lastName() {
        print("Apellido: ");
        String input = readLn();
        return input;
    }

    public static String address() {
        print("Direccion: ");
        String input = readLn();
        return input;
    }

    public static String phoneNumber() {
        print("Numero de Telefono: ");
        String input = readLn();
        return input;
    }

}
