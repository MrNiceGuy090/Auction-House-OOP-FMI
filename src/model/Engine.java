package model;

import java.util.UUID;

public class Engine {
    private int horsePower;
    private boolean isElectric;
    private double capacity;
    private String id;

    public Engine(int horsePower, boolean isElectric, double capacity){
        this.id = UUID.randomUUID().toString();
        this.horsePower = horsePower;
        this.isElectric = isElectric;
        this.capacity = capacity;
    }

    public Engine(String id, int horsePower, boolean isElectric, double capacity){
        this.id = id;
        this.horsePower = horsePower;
        this.isElectric = isElectric;
        this.capacity = capacity;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean electric) {
        isElectric = electric;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }
}
