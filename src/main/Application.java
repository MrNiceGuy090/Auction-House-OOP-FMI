package main;

import model.*;
import service.*;


import java.util.ArrayList;
import java.util.*;

public class Application {
    public static void main(String[] args){
        AuctionHouseService auctionService = new AuctionHouseService();
        Scanner scanner = new Scanner(System.in);

        boolean needUser = true;
        User activeUser = null;

        while(true) {
            boolean breakVar = false;
            if(needUser){
                System.out.println("Hello! Please log in or register if you do not have an account!\n1.Log in\n2.Register\n3.Exit");
                String opt = scanner.nextLine();
                activeUser = null;
                switch(opt) {
                    case("1"):
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Password");
                        String password = scanner.nextLine();

                        while( (activeUser = auctionService.logInUser(name, password)) == null ){
                            System.out.println("Name and password do not match. Want to try again?(Yes/No)");
                            String cont = scanner.nextLine();
                            if (cont.equals("No")) {breakVar = true; break; }
                            System.out.println("Name: ");
                            name = scanner.nextLine();
                            System.out.println("Password");
                            password = scanner.nextLine();
                        }
                        break;
                    case("2"):
                        System.out.println("Name: ");
                        name = scanner.nextLine();
                        System.out.println("Password");
                        password = scanner.nextLine();

                        while( !auctionService.addUser(name, password) ){
                            System.out.println("The name is already in use. Please choose another!");
                            System.out.println("Name: ");
                            name = scanner.nextLine();
                            System.out.println("Password");
                            password = scanner.nextLine();
                        }
                        activeUser = new User(name, password);
                        break;
                    case("3"):
                        return;
                }
                needUser = false;
            }
            if(breakVar) { needUser=true; continue;}

            if( activeUser.getName().equals("Admin")){
                ArrayList<User> users = auctionService.getUsers();
                int order = 1;
                for (User user: users){
                    System.out.println(String.valueOf(order) + ": Name: " + user.getName() + "; ID: " + user.getId() + "; Status: " + (user.isBannend() ? "Banned" : "Not banned") );
                    order += 1;
                }
                System.out.println("Type the index number of an user to change his status or 0 for exit");
                int nr = Integer.valueOf(scanner.nextLine());

                if (nr == 0) {needUser = true; activeUser = null; continue; }
                auctionService.banUser(users.get(nr-1));
                System.out.println("Succesfully changed status of user with ID " + users.get(nr-1).getId() );
                System.out.println("Want to exit?(Yes/No)");
                boolean exit = scanner.nextLine().equals("Yes") ? true : false;

                if(exit) break;
            }

            System.out.println("Please tell us what are you interested in!\n1.Buy\n2.Sell\n3.Just looking around\n4.Change user");
            String line = scanner.nextLine();
            switch(line) {
                case("1"):
                    System.out.println("Please choose an option:\n1.See ongoing auctions\n2.See auctions where you have the highest bid\n ");
                    String opt = scanner.nextLine();
                    switch (opt) {
                        case ("1"):
                            System.out.println("You can filter the ongoing auctions by the vehicle type(car/motorcycle/truck/bicycle/has engine) and sort by price or year of built.");
                            System.out.println("Filter (choose one vehicle type or none if not interested): ");
                            String filter = scanner.nextLine();
                            System.out.println("Sort (can choose between price and year, or none): ");
                            String sorter = scanner.nextLine();

                            ArrayList<Auction> auctions = auctionService.getAuctions(filter, sorter);
                            int order = 1;
                            for (Auction auction : auctions) {
                                System.out.println(String.valueOf(order) + ": Name: " + auction.getProduct().getName() + "\tHighest bid:" + String.valueOf(auction.getHighestBid().getPrice()) );
                                order +=1;
                            }

                            System.out.println("Type the number of an auction to see more details or 0 to reset the app");
                            int index = Integer.parseInt(scanner.nextLine());
                            if(index == 0) {continue;}
                            Auction auction = auctions.get(index - 1);
                            Product product = auction.getProduct();
                            System.out.println("Name: " + product.getName() + "\nBrand: " + product.getBrand() +
                                    "\nBuilt year: " + String.valueOf(product.getBuiltDate()) +
                                    "\nOwner's name: " + auction.getOwner().getName());
                            if( product instanceof Car ) {
                                Car car = (Car) product;
                                System.out.println("\nType: Car\nNumber of seats: " + String.valueOf(car.getNrOfSeats()) +
                                        "\n" + (car.isAutomatic() ? "Automatic" : "Manual") +
                                        "\nEngine horse power: " + String.valueOf(car.getEngine().getHorsePower()) +
                                        "\nEngine capacity: " + String.valueOf(car.getEngine().getCapacity()) +
                                        "\n" + (car.getEngine().isElectric() ? "Electric" : "On gas"));
                            }
                            else if (product instanceof Motorcycle){
                                Motorcycle motorcycle = (Motorcycle) product;
                                System.out.println("\nType: Motorcycle\nCategory: " + motorcycle.getCategory() +
                                        "\nEngine horse power: " + String.valueOf(motorcycle.getEngine().getHorsePower()) +
                                        "\nEngine capacity: " + String.valueOf(motorcycle.getEngine().getCapacity()) +
                                        "\n" + (motorcycle.getEngine().isElectric() ? "Electric" : "On gas"));
                            }
                            else if(product instanceof Truck) {
                                Truck truck = (Truck) product;
                                System.out.println("\nType: Truck\nCapacity: " + String.valueOf(truck.getCapacity()) +
                                        "\nNumber of wheels: " + String.valueOf(truck.getNoOfWheels()) +
                                        "\nEngine horse power: " + String.valueOf(truck.getEngine().getHorsePower()) +
                                        "\nEngine capacity: " + String.valueOf(truck.getEngine().getCapacity()) +
                                        "\n" + (truck.getEngine().isElectric() ? "Electric" : "On gas"));
                            }
                            else if(product instanceof Bicycle) {
                                    Bicycle bike = (Bicycle) product;
                                    System.out.println("\nType: Bicycle\nType: " + bike.getType());
                            }

                            System.out.println("Highest bid: " + String.valueOf(auction.getHighestBid().getPrice()));
                            System.out.println("1:Place bid\n2:See last bid\n3:Reset app ");
                            opt = scanner.nextLine();
                            switch (opt) {
                                case ("1"):
                                    System.out.println("Enter the sum you want to bid(it has to be greater than" + String.valueOf(auction.getHighestBid().getPrice())
                                            + "):");
                                    int sum = Integer.parseInt(scanner.nextLine());
                                    while (auctionService.placeBid(auction, activeUser, sum) == false) {
                                        System.out.println("The bid is too low. Please type a sum greater than " + String.valueOf(auction.getHighestBid().getPrice()) +
                                                "\nPlease type another sum: ");
                                        sum = Integer.parseInt(scanner.nextLine());
                                    }
                                    System.out.println("The offer has been successfully processed!");
                                    break;
                                case ("2"):
                                    System.out.println("Sum: " + String.valueOf(auctionService.getHighestBid(auction).getPrice()) + "\nOwner name: " +
                                            auctionService.getHighestBid(auction).getBidder().getName());
                                    break;
                                case ("3"):
                                    continue;
                            }
                        case("2"):
                            ArrayList<Auction> userActiveAuctions = activeUser.getActiveAuctions();
                            for(Auction auct: userActiveAuctions){
                                System.out.println(auct.getProduct().getName() + " " + String.valueOf(auct.getHighestBid().getPrice()));
                            }
                            break;
                    }
                    break;
                    case("2"):
                        System.out.println("Please choose an option:\n1.Sell vehicle\n2.See your vehicles put at auction\n ");
                        opt = scanner.nextLine();
                        switch(opt){
                                case("1"):
                                    System.out.println("Name: ");
                                    String name = scanner.nextLine();
                                    System.out.println("Brand: ");
                                    String brand = scanner.nextLine();
                                    System.out.println("Year of built: ");
                                    int builtDate = Integer.valueOf(scanner.nextLine());
                                    System.out.println("Starting price:");
                                    int startingPrice = Integer.valueOf(scanner.nextLine());
                                    System.out.println("Type (Car/Motorcycle/Bicycle/Truck):");
                                    String type = scanner.nextLine();

                                    switch(type) {
                                        case ("Car"):
                                            System.out.println("Number of seats");
                                            int nrOfSeats = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Is automatic? (Yes/No)");
                                            boolean isAutomatic = scanner.nextLine().equals("Yes") ? true : false;

                                            System.out.println("Engine horse power");
                                            int engineHP = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Engine capacity");
                                            int engineCapacity = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Is electric? (Yes/No)");
                                            boolean isElectric = scanner.nextLine().equals("Yes") ? true : false;

                                            Engine engine = new Engine(engineHP,isElectric, engineCapacity);
                                            Car car = new Car(name, brand, builtDate, nrOfSeats, isAutomatic, engine);

                                            auctionService.startAuction(car, activeUser, startingPrice);
                                            break;
                                        case("Motorcycle"):
                                            System.out.println("Category (Standard/Sport/Cruiser/Chopper/Offroad/Scooter): ");
                                            String category = scanner.nextLine();
                                            Motorcycle.Category cat = null;
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

                                            System.out.println("Engine horse power");
                                            engineHP = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Engine capacity");
                                            engineCapacity = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Is electric? (Yes/No)");
                                            isElectric = scanner.nextLine().equals("Yes") ? true : false;

                                            engine = new Engine(engineHP,isElectric, engineCapacity);
                                            Motorcycle motorcycle = new Motorcycle(name, brand, builtDate, cat, engine);

                                            auctionService.startAuction(motorcycle, activeUser, startingPrice);
                                            break;
                                        case("Truck"):
                                            System.out.println("Capacity: ");
                                            int capacity = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Number of wheels");
                                            int nrOfWheels = Integer.valueOf(scanner.nextLine());

                                            System.out.println("Engine horse power");
                                            engineHP = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Engine capacity");
                                            engineCapacity = Integer.valueOf(scanner.nextLine());
                                            System.out.println("Is electric? (Yes/No)");
                                            isElectric = scanner.nextLine().equals("Yes") ? true : false;

                                            engine = new Engine(engineHP,isElectric, engineCapacity);
                                            Truck truck = new Truck(name, brand, builtDate, capacity, nrOfWheels, engine);

                                            auctionService.startAuction(truck, activeUser, startingPrice);
                                            break;
                                        case("Bicycle"):
                                            System.out.println("Type (Road/Mountain/Cargo/BMX/Touring): ");
                                            String typeRead = scanner.nextLine();
                                            Bicycle.Type bikeType = null;
                                            switch(typeRead){
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
                                            Bicycle bike = new Bicycle(name, brand, builtDate, bikeType);

                                            auctionService.startAuction(bike , activeUser, startingPrice);
                                    }
                                    System.out.println("The vehicle has been put at action!");
                                    break;

                                case("2"):
                                    ArrayList<Auction> userAuctions = auctionService.getUserAuctionsByBid(activeUser);
                                    int order = 1;
                                    for(Auction auct : userAuctions){
                                        System.out.println(String.valueOf(order) + ": " + auct.getProduct().getName() + " " + String.valueOf(auct.getHighestBid().getPrice()) );
                                        order +=1;
                                    }
                                    System.out.println("Type the number of auction to close it or 0 to reset the app");
                                    int auctionToClose = Integer.valueOf(scanner.nextLine());
                                    if(auctionToClose == 0){
                                        continue;
                                    }
                                    else{
                                        auctionService.closeAuction(userAuctions.get(auctionToClose-1), activeUser);
                                        System.out.println("Auction succesfully closed!");
                                    }
                                    break;
                        }
                        break;
                case("3"):
                    System.out.println("1.See ended auctions and their winners\n2.See most expensive vehicles sold");
                    opt = scanner.nextLine();
                    switch(opt){
                        case("1"):
                            ArrayList<ArrayList<Object>> auctWinners = auctionService.getAuctionWinners();
                            for (ArrayList<Object> pair: auctWinners){
                                Auction auct = (Auction)pair.get(0);
                                User winner = (User)pair.get(1);
                                System.out.println("Product name: " + auct.getProduct().getName() + "; Winner name: " + winner.getName() +
                                        "; Bought for " + String.valueOf(auct.getHighestBid().getPrice())   );
                            }
                            break;
                        case("2"):
                            ArrayList<ArrayList<Object>> sortedAuctions = auctionService.getMostExpensiveProductsSold();
                            for (ArrayList<Object> pair: sortedAuctions) {
                                Product prod = (Product) pair.get(0);
                                double price = (Double) pair.get(1);
                                System.out.println("Product name: " + prod.getName() +
                                        "; Bought for " + String.valueOf(price) );
                            }
                            break;
                    }
                    break;
                case("4"):
                    needUser = true;
                    break;
            }
        }
    }
}

