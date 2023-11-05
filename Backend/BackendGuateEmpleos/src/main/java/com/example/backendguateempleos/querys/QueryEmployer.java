package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Job;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class QueryEmployer {
        //El Employer es 2
    private final Connection connection;

    public QueryEmployer() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public List<Job> publishedWorks(){
        String query = " SELECT jo.numberJobOffert, u.name AS userName, jo.nameOfJobOffert AS jobName, jo.description, jo.modality, jo.salary, jo.publicationDate, jo.details, c.nameCategorie AS categories FROM jobOffert jo INNER JOIN user u ON jo.cuiEmployer = u.cui LEFT JOIN categories c ON jo.numberCategorie = c.numberCategory";
        List<Job> jobOffers = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(query)){
            try(var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Job job = Job.builder()
                            .numberJobOffert(resultSet.getInt("numberJobOffert"))
                            .nameEmployer(resultSet.getString("userName"))
                            .nameOfJobOffert(resultSet.getString("jobName"))
                            .description(resultSet.getString("description"))
                            .modality(resultSet.getString("modality"))
                            .salary(resultSet.getDouble("salary"))
                            .publicationDate(Date.valueOf(resultSet.getDate("publicationDate").toLocalDate()))
                            .details(resultSet.getString("details"))
                            .category(resultSet.getString("categories"))
                            .build();
                    jobOffers.add(job);
                    System.out.println(job.toString());
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobOffers;
    }

}
