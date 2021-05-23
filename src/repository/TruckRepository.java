package repository;

import config.*;
import model.*;

import java.sql.*;
import java.util.*;

public class TruckRepository {

    public void addTruck(Truck truck) {

        String sqlEngine = "insert into engines values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlEngine)) {//try with resources
            statement.setString(1, truck.getEngine().getId());
            statement.setDouble(2, truck.getEngine().getHorsePower());
            statement.setBoolean(3, truck.getEngine().isElectric());
            statement.setDouble(4, truck.getEngine().getCapacity());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlProduct = "insert into products values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlProduct)) {//try with resources
            statement.setString(1, truck.getId());
            statement.setString(2, truck.getName());
            statement.setString(3, truck.getBrand());
            statement.setInt(4, truck.getBuiltDate());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqltruck = "insert into trucks values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqltruck)) {//try with resources
            statement.setString(1, truck.getId());
            statement.setLong(2, truck.getCapacity());
            statement.setInt(3, truck.getNoOfWheels());
            statement.setString(4, truck.getEngine().getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }


    }

    public Optional<Truck> getTruckById(String id) {
        String name = "";
        String brand = "";
        int builtDate = 0;
        int capacity = 0;
        long noOfWheels = 0;
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

        sql = "select * from trucks where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                capacity = result.getInt("capacity");
                noOfWheels = result.getLong("noOfWheels");
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

        return Optional.of(new Truck(id, name,brand, builtDate, capacity, (int) noOfWheels, new Engine( String.valueOf(engineId), engineHorsePower, engineIsElectric, engineCapacity) ) );
    }


    public void updatetruck(Truck truck) {
        String engineId = "";

        // get engineId
        String sql = "select * from trucks where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, truck.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                engineId = result.getString("engine");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE engines SET horsePower = ?, isElectric = ?, capacity = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, truck.getEngine().getHorsePower());
            statement.setBoolean(2, truck.getEngine().isElectric());
            statement.setDouble(3, truck.getEngine().getCapacity());
            statement.setString(4, engineId);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE trucks SET nrOfSeats = ?, isAutomatic = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, truck.getCapacity());
            statement.setLong(2, truck.getNoOfWheels());
            statement.setString(3, truck.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE products SET name = ?, brand = ?, builtDate = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, truck.getName());
            statement.setString(2, truck.getBrand());
            statement.setInt(3, truck.getBuiltDate());
            statement.setString(4, truck.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    public void deletetruckByID(String id) {

        String sql = "delete from products where id = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}