package model;

import java.util.Date;

public class Truck extends Product {
    private long capacity;
    private int noOfWheels;
    private Engine engine;

    public Truck(String name, String brand, int builtDate, long capacity, int noOfWheels, Engine engine){
        super(name, brand, builtDate);
        this.capacity = capacity;
        this.noOfWheels = noOfWheels;
        this.engine = engine;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public int getNoOfWheels() {
        return noOfWheels;
    }

    public void setNoOfWheels(int noOfWheels) {
        this.noOfWheels = noOfWheels;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
