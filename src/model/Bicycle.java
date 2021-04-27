package model;

import java.util.Date;

public class Bicycle extends Product{
    public enum Type{
        ROAD,
        MOUNTAIN,
        CARGO,
        BMX,
        TOURING
    }

    private Type type;

    public Bicycle(String name, String brand, int builtDate,Type type){
        super(name, brand, builtDate);
        this.type = type;
    }
    public Bicycle(String id,String name, String brand, int builtDate,Type type){
        super(id, name, brand, builtDate);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
