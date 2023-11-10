package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryVisits {

    private final Connection connection;

    public QueryVisits() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public void updateNumberVisits(int number){
        String query = "UPDATE numberOfVisits SET numberVisit = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error in update number visits: " + e);
        }
    }

    public int numberVisits(){
        String query = "SELECT * FROM numberOfVisits WHERE codVisit = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,1);
            try(var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return resultSet.getInt("numberVisit");
                }
            }
        }catch (SQLException e){
            System.out.println("Error in numberVisits");
        }
        return 0;
    }

    public void insertNumberVisits(){
        String query = "INSERT INTO numberOfVisits (numberVisit) VALUES (?)";
        try(var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error in insert Number Visits: " + e);
        }
    }

    public boolean verifyVisits(){
        String query = "SELECT * FROM numberOfVisits";
        try (var preparedStatement = connection.prepareStatement(query)){
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println("Error in verify visits: " + e);
        }
        return false;
    }
}
