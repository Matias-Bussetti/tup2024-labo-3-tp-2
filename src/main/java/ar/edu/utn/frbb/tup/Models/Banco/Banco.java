package ar.edu.utn.frbb.tup.Models.Banco;

import java.util.ArrayList;

import ar.edu.utn.frbb.tup.Models.Client.Client;

public class Banco {
    private static int nextId = 0;
    private ArrayList<Client> clients = new ArrayList<>();

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public static int getNextId() {
        return nextId++;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }
}
