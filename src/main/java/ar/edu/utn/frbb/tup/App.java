package ar.edu.utn.frbb.tup;

import java.io.IOException;

import ar.edu.utn.frbb.tup.Application.Application;
import ar.edu.utn.frbb.tup.Controller.AccountController;
import ar.edu.utn.frbb.tup.Controller.ClientController;
import ar.edu.utn.frbb.tup.Controller.MovementController;
import ar.edu.utn.frbb.tup.Models.Account.Account;
import ar.edu.utn.frbb.tup.Models.Account.AccountInput;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Models.Client.Client;
import ar.edu.utn.frbb.tup.Models.Client.ClientInput;
import ar.edu.utn.frbb.tup.Utils.Console;
import ar.edu.utn.frbb.tup.Utils.TextColor;

/**
 * Hello world!
 *
 */
public class App {
    static void myMethod() {
        System.out.println("Hello World!");
    }

    public static void main(String[] args) throws IOException {
        Banco banco = new Banco();

        Application.initialize(banco).start();
    }
}
