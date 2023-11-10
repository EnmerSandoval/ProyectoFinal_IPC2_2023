package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Commission;
import com.example.backendguateempleos.model.Data;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryData {
    private final Connection connection;

    public QueryData() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public int getViewsVisits(){
        String query = "SELECT * FROM numberOfVisits";

        Data data = new Data();
        try (var preparedStatement = connection.prepareStatement(query)){
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var numberViews = resultSet.getInt("numberVisit");
                               data = data.builder().numberViews(numberViews).build();
                }
            }
        } catch (SQLException e){
            System.out.println("Error in get View visits: " + e);
        }
        return data.getNumberViews();
    }

    public int numberEmployers(){
        String query = "SELECT COUNT(*) NumberOfUsers FROM user WHERE typeOfUser = 2";
        int number = 0;
        try (var preparedStatement = connection.prepareStatement(query)){
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    number = resultSet.getInt("NumberOfUsers");
                }
            }
        } catch (SQLException e){
            System.out.println("Error in number Employers: " + e);
        }
        return number;
    }

    public int numberApplicants(){
        String query = "SELECT COUNT(*) NumberOfUsers FROM user WHERE typeOfUser = 3";
        int number = 0;
        try (var preparedStatement = connection.prepareStatement(query)){
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    number = resultSet.getInt("NumberOfUsers");
                }
            }
        } catch (SQLException e){
            System.out.println("Error in number Employers: " + e);
        }
        return number;
    }

    public int amountCommission(){
        String query = "SELECT * FROM commission WHERE numberCommission = ?";
        int amount = 0;
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, 1);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    amount = resultSet.getInt("amount");
                }
            }
        }catch (SQLException e){
            System.out.println("Error in amount Commission please check: " + e);
        }
        return amount;
    }

    public boolean updateCommision(Commission commission){
        String query = "UPDATE commission SET amount = ? WHERE numberCommission = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, commission.getAmount());
            preparedStatement.setInt(2, commission.getNumberCommission());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Error in update Commission please check: " + e);
        }
        return false;
    }
}
