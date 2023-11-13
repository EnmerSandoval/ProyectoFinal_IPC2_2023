package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Job;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QueryJob {
    private final Connection connection;

    public QueryJob() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public Optional<Job> getJobByNumberOffert(Job job){
        String query = "SELECT jobOffert.nameOfJobOffert, jobOffert.description, categories.nameCategory AS category, jobOffert.location, jobOffert.salary, jobOffert.modality, user.name AS employer FROM jobOffert JOIN user ON jobOffert.cuiEmployer = user.cui JOIN categories ON jobOffert.numberCategorie = categories.numberCategory WHERE jobOffert.numberJobOffert = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1 , job.getNumberJobOffert());
            try (var resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    job.setNameEmployer(resultSet.getString("employer"));
                    job.setNameOfJobOffert(resultSet.getString("nameOfJobOffert"));
                    job.setDescription(resultSet.getString("description"));
                    job.setCategory(resultSet.getString("category"));
                    job.setLocation(resultSet.getString("location"));
                    job.setSalary(resultSet.getInt("salary"));
                    job.setModality(resultSet.getString("modality"));
                    return Optional.ofNullable(job);
                }
            }
        } catch (SQLException e){
            System.out.println("Error in getJobByNumberOffert please check: " + e);
        }
        return Optional.empty();
    }
}
