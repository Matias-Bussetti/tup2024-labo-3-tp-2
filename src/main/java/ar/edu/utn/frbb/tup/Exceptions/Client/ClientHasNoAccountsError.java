package ar.edu.utn.frbb.tup.Exceptions.Client;

//https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java <- de donde lo robe, digo donde me "inspire"

public class ClientHasNoAccountsError extends Exception {
    // Parameterless Constructor
    public ClientHasNoAccountsError() {
    }

    // Constructor that accepts a message
    public ClientHasNoAccountsError(String message) {
        super(message);
    }
}
