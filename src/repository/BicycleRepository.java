package repository;

import config.*;
import model.*;

import java.sql.*;
import java.util.*;

public class BicycleRepository {

    public void addBicycle(Bicycle bicycle) {
        
        String sqlProduct = "insert into products values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlProduct)) {//try with resources
            statement.setString(1, bicycle.getId());
            statement.setString(2, bicycle.getName());
            statement.setString(3, bicycle.getBrand());
            statement.setInt(4, bicycle.getBuiltDate());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlbicycle = "insert into bicycles values (?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlbicycle)) {//try with resources
            statement.setString(1, bicycle.getId());
            statement.setString(2, bicycle.getType().toString());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }


    }

    public Optional<Bicycle> getBicycleById(String id) {
        String name = "";
        String brand = "";
        int builtDate = 0;
        Bicycle.Type type = Bicycle.Type.ROAD;


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

        sql = "select * from bicycles where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                type = Bicycle.Type.valueOf(result.getString("type"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }


        return Optional.of(new Bicycle(id, name,brand, builtDate, type) );
    }


    public void updateBicycle(Bicycle bicycle) {

        String sql = "UPDATE bicycles SET type = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, bicycle.getType().toString());
            statement.setString(2, bicycle.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE products SET name = ?, brand = ?, builtDate = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, bicycle.getName());
            statement.setString(2, bicycle.getBrand());
            statement.setInt(3, bicycle.getBuiltDate());
            statement.setString(4, bicycle.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteBicycleByID(String id) {

        String sql = "delete from products where id = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}