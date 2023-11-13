package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Commission;
import com.example.backendguateempleos.model.Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    public boolean updateCommissionAndHistory(Commission commission) {
        try {
            updateCommision(commission);

            if (hasPreviousHistoryWithAmount(commission.getBeforeAmount())) {
                updateHistoryEndDate(commission.getBeforeAmount());
            }

            insertHistory(commission);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in updateCommissionAndHistory: " + e);
        }
        return false;
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

    private boolean hasPreviousHistoryWithAmount(int amount) throws SQLException {
        String query = "SELECT 1 FROM commissionsHistory WHERE  amount = ? AND dateCommissionsEnd IS NULL";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, amount);
            try (var resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }



    private void updateHistoryEndDate(int amount) throws SQLException {
        String query = "UPDATE commissionsHistory SET dateCommissionsEnd = CURRENT_DATE WHERE amount = ? AND dateCommissionsEnd IS NULL";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.executeUpdate();
        }
    }




    private void insertHistory(Commission commission)  {
        String query = "INSERT INTO commissionsHistory (dateCommission, amount) VALUES (CURRENT_DATE, ?)";
        try (var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, commission.getAmount());
            preparedStatement.executeUpdate();

            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in insert history please check:  " + e);
        }
    }
}
