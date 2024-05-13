package ar.edu.utn.frbb.tup.Application;

import ar.edu.utn.frbb.tup.Controller.AccountController;
import ar.edu.utn.frbb.tup.Controller.ClientController;
import ar.edu.utn.frbb.tup.Controller.MovementController;
import ar.edu.utn.frbb.tup.Models.Banco.Banco;
import ar.edu.utn.frbb.tup.Utils.Console;
import ar.edu.utn.frbb.tup.Utils.TextColor;

public class Application {
    private Banco banco;

    private Application(Banco banco) {
        this.banco = banco;
    }

    public void menu() {
        Console.outLn(TextColor.inYellow("0 - Para Salir"));
        Console.outLn("1 - Mostar Usuarios");
        Console.outLn("2 - Agregar Usuario");
        Console.outLn("3 - Actualizar Usuario");
        Console.outLn("4 - Eliminar Usuario");
        Console.outLn("- - - - - - - -");
        Console.outLn("5 - Mostrar cuentas de cliente");
        Console.outLn("6 - Agregar cuenta a cliente");
        Console.outLn("- - - - - - - -");
        Console.outLn("7 - Consultar Saldo");
        Console.outLn("8 - Hacer Deposito");
        Console.outLn("9 - Hacer Retiro");
        Console.outLn("10 - Hacer Transferencia");
        Console.outLn("- - - - - - - -");
        Console.outLn("11 - Mostrar Movimientos de Cuenta");
    }

    public void acctions(String option) throws Exception {
        switch (option) {
            case "0":
                Console.outLn(TextColor.inCyan("Hasta luego..."));
                break;
            case "1":
                ClientController.printClients(this.banco);
                break;
            case "2":
                ClientController.storeClient(this.banco);
                break;
            case "3":
                ClientController.updateClient(this.banco);
                break;
            case "4":
                ClientController.deleteClient(this.banco);
                break;
            case "5":
                ClientController.printClientAccounts(this.banco);
                break;
            case "6":
                AccountController.addAccount(this.banco);
                break;
            case "7":
                AccountController.accountBalance(this.banco);
                break;
            case "8":
                MovementController.makeDeposit(this.banco);
                break;
            case "9":
                MovementController.makeWithdraw(this.banco);
                break;
            case "10":
                MovementController.makeTransfer(this.banco);
                break;
            case "11":
                MovementController.printClientAcountMovements(this.banco);
                break;
            default:
                Console.outLn(TextColor.inYellow("Operacion no permitida"));
                break;
        }
    }

    public static Application initialize(Banco banco) {
        return new Application(banco);
    }

    public void start() {
        String inputOption = "";
        Console.clearScreen();
        do {

            menu();

            boolean operation = true;
            String operationMessage = "";
            try {
                inputOption = Console.inLn();
                acctions(inputOption);

            } catch (Exception e) {
                operation = false;
                operationMessage = e.getMessage();
            } finally {
                Console.outLn("Resultado de la operacion: "
                        + (operation ? TextColor.inGreen("Exitoso")
                                : TextColor.inRed("Sucedio un error (" + operationMessage + ")")));
            }

        } while (!inputOption.equals("0"));
    }

}
