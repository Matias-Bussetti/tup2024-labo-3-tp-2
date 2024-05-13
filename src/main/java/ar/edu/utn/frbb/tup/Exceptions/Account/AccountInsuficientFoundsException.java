package ar.edu.utn.frbb.tup.Exceptions.Account;

public class AccountInsuficientFoundsException extends Exception {
    // Parameterless Constructor
    public AccountInsuficientFoundsException() {
    }

    // Constructor that accepts a message
    public AccountInsuficientFoundsException(String message) {
        super(message);
    }

}
