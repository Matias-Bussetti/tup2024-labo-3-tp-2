package ar.edu.utn.frbb.tup.Models.Account;

import java.time.Instant;
import java.util.ArrayList;

import ar.edu.utn.frbb.tup.Models.Movement.Movement;

public class Account {
    private int id;
    private String type;
    private float balance;
    private ArrayList<Movement> movements = new ArrayList<>();
    private Instant createdAt;

    public Account(int id, String type) {
        this.id = id;
        this.type = type;
        this.balance = 0;
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", type=" + type + ", balance=" + balance + ", movements=" + movements
                + ", createdAt="
                + createdAt + "]";
    }

    public void sumBalance(float sum) {
        this.balance += sum;
    }

    public void addMovement(Movement movement) {
        this.movements.add(movement);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public ArrayList<Movement> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Movement> movements) {
        this.movements = movements;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
