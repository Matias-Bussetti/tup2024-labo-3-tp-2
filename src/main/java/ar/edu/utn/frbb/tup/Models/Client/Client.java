package ar.edu.utn.frbb.tup.Models.Client;

import java.util.ArrayList;

import ar.edu.utn.frbb.tup.Models.Account.Account;

public class Client {
    private int id;
    private String name;
    private String lastName;
    private String address;
    private String phoneNumber;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Client(int id, String name, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", lastName=" + lastName + ", address=" + address
                + ", phoneNumber=" + phoneNumber + ", accounts=" + accounts + "]";
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

}
