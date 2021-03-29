package model;

import java.util.Date;
import java.util.UUID;

public abstract class Product implements Comparable<Product> {
    protected String id;
    protected String name;
    protected String brand;
    protected int builtDate;

    public Product(String name, String brand, int builtDate) {
        this.id = UUID.randomUUID().toString();;
        this.name = name;
        this.brand = brand;
        this.builtDate = builtDate;
    }

    @Override
    public int compareTo(Product otherProduct) {
        return Integer.compare(getBuiltDate(), otherProduct.getBuiltDate());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(int builtDate) {
        this.builtDate = builtDate;
    }
}
