package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Card;
import com.example.backendguateempleos.model.Employer;
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
        String query = " SELECT jo.numberJobOffert, u.name AS userName, jo.nameOfJobOffert AS jobName, jo.description, jo.modality, jo.salary, jo.publicationDate, jo.details, c.nameCategory AS categories FROM jobOffert jo INNER JOIN user u ON jo.cuiEmployer = u.cui LEFT JOIN categories c ON jo.numberCategorie = c.numberCategory";
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
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobOffers;
    }

    public boolean insertEmployerDescription(Employer employer){
        String query = "INSERT INTO employer (cuiEmployer, mission, vision) VALUES ( ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, employer.getCui());
            preparedStatement.setString(2, employer.getMission());
            preparedStatement.setString(3, employer.getVision());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println("Error in insert employer description: " + e);
        }
        return false;
    }

    public boolean insertCardEmployer(Card card) {
        String query = "INSERT INTO chargeCard (numberCard, nameChargeCard, cuiEmployer, cvv) VALUES ( ?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, card.getNumberCard());
            preparedStatement.setString(2, card.getNameChargeCard());
            preparedStatement.setInt(3, card.getCuiEmployer());
            preparedStatement.setInt(4, card.getCvv());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Error in insert card employer: " + e);
        }
        return false;
    }
}
