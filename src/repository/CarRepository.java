package repository;

import config.*;
import model.*;

import java.sql.*;
import java.util.*;

public class CarRepository {

    public void addCar(Car car) {

        String sqlEngine = "insert into engines values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlEngine)) {//try with resources
            statement.setString(1, car.getEngine().getId());
            statement.setDouble(2, car.getEngine().getHorsePower());
            statement.setBoolean(3, car.getEngine().isElectric());
            statement.setDouble(4, car.getEngine().getCapacity());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlProduct = "insert into products values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlProduct)) {//try with resources
            statement.setString(1, car.getId());
            statement.setString(2, car.getName());
            statement.setString(3, car.getBrand());
            statement.setInt(4, car.getBuiltDate());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlCar = "insert into cars values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlCar)) {//try with resources
            statement.setString(1, car.getId());
            statement.setInt(2, car.getNrOfSeats());
            statement.setBoolean(3, car.isAutomatic());
            statement.setString(4, car.getEngine().getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }


    }

    public Optional<Car> getCarById(String id) {
        String name = "";
        String brand = "";
        int builtDate = 0;
        int nrOfSeats = 0;
        boolean isAutomatic = false;
        int engineHorsePower = 0;
        int engineCapacity = 0;
        boolean engineIsElectric = false;
        String engineId = "";


        String sql = "select * from products where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                //i have at least one record in the result set
                name = result.getString("name");
                brand = result.getString("brand");
                builtDate = result.getInt("builtDate");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "select * from cars where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                nrOfSeats = result.getInt("nrOfSeats");
                isAutomatic = result.getBoolean("isAutomatic");
                engineId = result.getString("engine");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "select * from engines where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, engineId);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                engineHorsePower = result.getInt("horsePower");
                engineIsElectric = result.getBoolean("isElectric");
                engineCapacity = result.getInt("capacity");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(new Car(id, name,brand, builtDate, nrOfSeats, isAutomatic, new Engine( String.valueOf(engineId), engineHorsePower, engineIsElectric, engineCapacity) ) );
    }


    public void updateCar(Car car) {
        String engineId = "";

        // get engineId
        String sql = "select * from cars where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, car.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                engineId = result.getString("engine");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE engines SET horsePower = ?, isElectric = ?, capacity = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, car.getEngine().getHorsePower());
            statement.setBoolean(2, car.getEngine().isElectric());
            statement.setDouble(3, car.getEngine().getCapacity());
            statement.setString(4, engineId);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE cars SET nrOfSeats = ?, isAutomatic = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, car.getNrOfSeats());
            statement.setBoolean(2, car.isAutomatic());
            statement.setString(3, car.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE products SET name = ?, brand = ?, builtDate = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getBrand());
            statement.setInt(3, car.getBuiltDate());
            statement.setString(4, car.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteCarByID(String id) {

        String sql = "delete from products where id = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}