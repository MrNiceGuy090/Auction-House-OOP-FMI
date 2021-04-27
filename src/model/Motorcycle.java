package model;

public class Motorcycle extends Product{
    public enum Category {
        STANDARD,
        SPORT,
        CRUISER,
        CHOPPER,
        OFFROAD,
        SCOOTER,
    }

    private Category category;
    private  Engine engine;

    public Motorcycle(String name, String brand, int builtDate,Category category, Engine engine){
        super(name, brand, builtDate);
        this.category = category;
        this.engine = engine;
    }
    public Motorcycle(String id, String name, String brand, int builtDate,Category category, Engine engine){
        super(id, name, brand, builtDate);
        this.category = category;
        this.engine = engine;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
