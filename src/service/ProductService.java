package service;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static ProductService instance = null;
    private List<Product> products;

    private ProductService(){
        products = new ArrayList<Product>();
    }

    public static ProductService getInstance()
    {
        if (instance == null)
            instance = new ProductService();

        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void insertProduct(Product product){
        products.add(product);
    }

    public Car buildCar(String id, String name, String brand, int builtDate, int nrOfSeats, boolean isAutomatic, int horsePower, boolean isElectric, double capacity){
        Engine engine = new Engine(horsePower, isElectric, capacity);
        Car car = new Car(id, name, brand, builtDate, nrOfSeats, isAutomatic, engine);
        return car;
    }

    public Truck buildTruck(String id,String name, String brand, int builtDate, long truckCapacity, int noOfWheels,  int horsePower, boolean isElectric, double capacity){
        Engine engine = new Engine(horsePower, isElectric, capacity);
        Truck truck = new Truck(id, name, brand, builtDate, truckCapacity,noOfWheels, engine);
        return truck;
    }
    public Motorcycle buildMotorcycle(String id,String name, String brand, int builtDate, String category,int horsePower, boolean isElectric, double capacity ){
        Engine engine = new Engine(horsePower, isElectric, capacity);
        Motorcycle.Category cat = Motorcycle.Category.STANDARD;
        switch(category){
            case("Standard"):
                cat = Motorcycle.Category.STANDARD;
                break;
            case("Sport"):
                cat = Motorcycle.Category.SPORT;
                break;
            case("Cruiser"):
                cat = Motorcycle.Category.CRUISER;
                break;
            case("Chopper"):
                cat = Motorcycle.Category.CHOPPER;
                break;
            case("Offroad"):
                cat = Motorcycle.Category.OFFROAD;
                break;
            case("Scooter"):
                cat = Motorcycle.Category.SCOOTER;
                break;
        }
        Motorcycle moto = new Motorcycle(id, name,brand,builtDate,cat,engine);
        return moto;
    }

    public Bicycle buildBicycle(String id, String name, String brand, int builtDate, String type){
        Bicycle.Type bikeType = Bicycle.Type.ROAD;
        switch(type){
            case("Road"):
                bikeType = Bicycle.Type.ROAD;
                break;
            case("Mountain"):
                bikeType = Bicycle.Type.MOUNTAIN;
                break;
            case("Cargo"):
                bikeType = Bicycle.Type.CARGO;
                break;
            case("BMX"):
                bikeType = Bicycle.Type.BMX;
                break;
            case("Touring"):
                bikeType = Bicycle.Type.TOURING;
                break;
        }
        Bicycle bike = new Bicycle(id,name,brand,builtDate,bikeType);
        return bike;
    }

    public void readProducts(){
        ReaderService reader = ReaderService.getInstance();
        String text = reader.readFile("src\\files\\Products.csv");
        String[] lines = text.split("\n");
        boolean skip = true;
        for(String line : lines){
            if( skip){
                skip = false;
                continue;
            }
            String[] data = line.split(",");

            if (data[4].equals("Car")){
                Car car = this.buildCar(data[0], data[1], data[2],Integer.parseInt(data[3]),Integer.parseInt(data[5]), Boolean.getBoolean(data[6]), Integer.parseInt(data[7]),Boolean.getBoolean(data[9]),Double.parseDouble(data[8])  );
                products.add(car);
            }
            else if (data[4].equals("Truck")){
                Truck truck = this.buildTruck(data[0], data[1], data[2], Integer.parseInt(data[3]),Integer.parseInt(data[5]), Integer.parseInt(data[6]),Integer.parseInt(data[7]),Boolean.getBoolean(data[9]),Double.parseDouble(data[8]));
                products.add(truck);
            }
            else if (data[4].equals("Motorcycle")){
                Motorcycle moto = this.buildMotorcycle(data[0],data[1], data[2],Integer.parseInt(data[3]), data[5],Integer.parseInt(data[6]),Boolean.getBoolean(data[8]),Double.parseDouble(data[7]) );
                 products.add(moto);
            }
            else if (data[4].equals("Bicycle")){
                Bicycle bike =  this.buildBicycle(data[0], data[1], data[2],Integer.parseInt(data[3]), data[5] );
                products.add(bike);
            }
        }
    }
}
