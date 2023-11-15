package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.model.Job;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryApplicant {
    private final Connection connection;

    public QueryApplicant() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public boolean insertCategoriesForApplicant(List<Category> categoriesList, int cuiApplicant) {
        String insertQuery = "INSERT INTO ownCategory (cuiApplicant, numberCategory) VALUES (?, ?)";

        try (var preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Category category : categoriesList) {
                preparedStatement.setInt(1, cuiApplicant);
                preparedStatement.setInt(2, category.getNumberCategory());
                preparedStatement.addBatch();
            }
            int[] result = preparedStatement.executeBatch();
            for (int i : result) {
                if (i <= 0) {
                    return false;
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("error in SQL exception:  " + e);
            return false;
        }
    }

    public List<Job> recomendedJobs(int cui) {
        String query = "SELECT j.*, u.name AS employer_name, c.nameCategory FROM jobOffert j JOIN user u ON j.cuiEmployer = u.cui JOIN categories c ON j.numberCategorie = c.numberCategory WHERE j.state > 0 AND j.numberCategorie NOT IN (SELECT numberCategory FROM ownCategory WHERE cuiApplicant = ?) ORDER BY RAND() LIMIT 5";
        List<Job> jobOffers = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cui);

            try(var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Job job = Job.builder()
                            .cuiEmployer(resultSet.getInt("cuiEmployer"))
                            .numberJobOffert(resultSet.getInt("numberJobOffert"))
                            .nameEmployer(resultSet.getString("employer_name"))
                            .nameOfJobOffert(resultSet.getString("nameOfJobOffert"))
                            .description(resultSet.getString("description"))
                            .location(resultSet.getString("location"))
                            .modality(resultSet.getString("modality"))
                            .salary(resultSet.getDouble("salary"))
                            .publicationDate(Date.valueOf(resultSet.getDate("publicationDate").toLocalDate()))
                            .details(resultSet.getString("details"))
                            .category(resultSet.getString("nameCategory"))
                            .build();
                    jobOffers.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobOffers;
    }

    public boolean insertRequest(int cui, int numberJobOffert, String postulationReason){
        String query = "INSERT INTO request (cuiApplicant, numberJobOffert, message, state) VALUES (?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, cui);
            preparedStatement.setInt(2, numberJobOffert);
            preparedStatement.setString(3, postulationReason);
            preparedStatement.setBoolean(4, true);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Error in insert Request: " + e);
        }
        return false;
    }
}
