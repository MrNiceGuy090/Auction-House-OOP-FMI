package model;

import java.util.Date;

public class Car extends Product{
    private int nrOfSeats;
    private boolean isAutomatic;
    private Engine engine;

    public Car(String name, String brand, int builtDate, int nrOfSeats, boolean isAutomatic, Engine engine){
        super(name, brand, builtDate);
        this.nrOfSeats = nrOfSeats;
        this.isAutomatic = isAutomatic;
        this.engine = engine;
    }

    public Car(String id, String name, String brand, int builtDate, int nrOfSeats, boolean isAutomatic, Engine engine){
        super(id, name, brand, builtDate);
        this.nrOfSeats = nrOfSeats;
        this.isAutomatic = isAutomatic;
        this.engine = engine;
    }

    public int getNrOfSeats() {
        return nrOfSeats;
    }

    public void setNrOfSeats(int nrOfSeats) {
        this.nrOfSeats = nrOfSeats;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
