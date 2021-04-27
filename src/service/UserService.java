package service;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UserService {
    private static UserService instance = null;
    private List<User> users;
    private WriterService writerService = WriterService.getInstance();

    private UserService() {
        users = new ArrayList<User>();
    }

    public static UserService getInstance()
    {
        if (instance == null)
            instance = new UserService();

        return instance;
    }



    public List<User> getUsers() {
        writerService.writeToFile("getUsers", new Date(System.currentTimeMillis()));
        return users;
    }

    public boolean addUser(String name, String password){
        writerService.writeToFile("addUser", new Date(System.currentTimeMillis()));
        for(User u: users){
            if(u.getName().equals(name)) {
                return false;
            }
        }
        User user = new User(name, password);
        users.add(user);
        users.sort(Comparator.comparing(User::getName));
        return true;
    }

    public boolean addUser(String id, String name, String password){
        writerService.writeToFile("addUser", new Date(System.currentTimeMillis()));
        for(User u: users){
            if(u.getName().equals(name)) {
                return false;
            }
        }
        User user = new User(id, name, password);
        users.add(user);
        users.sort(Comparator.comparing(User::getName));
        return true;
    }

    public User logInUser(String name, String password){
        writerService.writeToFile("logInUser", new Date(System.currentTimeMillis()));
        for(User u: users){
            if(u.getName().equals(name) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void readUsers(){
        ReaderService reader = ReaderService.getInstance();
        String text = reader.readFile("src\\files\\Users.csv");
        String[] lines = text.split("\n");
        boolean skip = true;
        for(String line : lines){
            if( skip){
                skip = false;
                continue;
            }
            String[] data = line.split(",");
            this.addUser(data[0], data[1], data[2]);
        }
    }

}
