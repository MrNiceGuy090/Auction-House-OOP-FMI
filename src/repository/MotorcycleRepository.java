package repository;

import config.*;
import model.*;

import java.sql.*;
import java.util.*;

public class MotorcycleRepository {

    public void addMotorcycle(Motorcycle motorcycle) {

        String sqlEngine = "insert into engines values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlEngine)) {//try with resources
            statement.setString(1, motorcycle.getEngine().getId());
            statement.setDouble(2, motorcycle.getEngine().getHorsePower());
            statement.setBoolean(3, motorcycle.getEngine().isElectric());
            statement.setDouble(4, motorcycle.getEngine().getCapacity());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlProduct = "insert into products values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlProduct)) {//try with resources
            statement.setString(1, motorcycle.getId());
            statement.setString(2, motorcycle.getName());
            statement.setString(3, motorcycle.getBrand());
            statement.setInt(4, motorcycle.getBuiltDate());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sqlmotorcycle = "insert into motorcycles values (?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sqlmotorcycle)) {//try with resources
            statement.setString(1, motorcycle.getId());
            statement.setString(2, motorcycle.getCategory().toString());
            statement.setString(3, motorcycle.getEngine().getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }


    }

    public Optional<Motorcycle> getMotorcycleById(String id) {
        String name = "";
        String brand = "";
        int builtDate = 0;
        int engineHorsePower = 0;
        int engineCapacity = 0;
        Motorcycle.Category category = Motorcycle.Category.STANDARD;
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

        sql = "select * from motorcycles where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                category = Motorcycle.Category.valueOf(result.getString("category"));
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

        return Optional.of(new Motorcycle(id, name,brand, builtDate, category, new Engine( String.valueOf(engineId), engineHorsePower, engineIsElectric, engineCapacity) ) );
    }


    public void updateMotorcycle(Motorcycle motorcycle) {
        String engineId = "";

        // get engineId
        String sql = "select * from motorcycles where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, motorcycle.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                engineId = result.getString("engine");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE engines SET horsePower = ?, isElectric = ?, capacity = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, motorcycle.getEngine().getHorsePower());
            statement.setBoolean(2, motorcycle.getEngine().isElectric());
            statement.setDouble(3, motorcycle.getEngine().getCapacity());
            statement.setString(4, engineId);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE motorcycles SET category = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, motorcycle.getCategory().toString());
            statement.setString(2, motorcycle.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        sql = "UPDATE products SET name = ?, brand = ?, builtDate = ? WHERE id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, motorcycle.getName());
            statement.setString(2, motorcycle.getBrand());
            statement.setInt(3, motorcycle.getBuiltDate());
            statement.setString(4, motorcycle.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteMotorcycleByID(String id) {

        String sql = "delete from products where id = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}