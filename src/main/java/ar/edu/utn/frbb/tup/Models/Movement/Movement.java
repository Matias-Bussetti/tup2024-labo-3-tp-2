package ar.edu.utn.frbb.tup.Models.Movement;

import java.time.Instant;

public class Movement {
    protected Instant createdAt;
    protected float amount;

    public Movement(float amount) {
        this.amount = amount;
        this.createdAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
