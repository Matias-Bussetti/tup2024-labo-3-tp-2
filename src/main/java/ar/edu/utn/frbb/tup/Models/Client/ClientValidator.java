package ar.edu.utn.frbb.tup.Models.Client;

import ar.edu.utn.frbb.tup.Exceptions.Client.ClientHasNoAccountsError;

public class ClientValidator {
    private Client client;

    private ClientValidator(Client client) {
        this.client = client;
    }

    public static ClientValidator validate(Client client) {
        return new ClientValidator(client);
    }

    public void hasAccounts() throws ClientHasNoAccountsError {
        if (this.client.getAccounts().size() == 0) {
            throw new ClientHasNoAccountsError("El cliente necesita al menos una cuenta para seguir...");
        }
    }
}
