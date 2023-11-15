package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Job;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryJob {
    private final Connection connection;

    public QueryJob() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public Optional<Job> getJobByNumberOffert(Job job){
        String query = "SELECT jobOffert.nameOfJobOffert, jobOffert.description, categories.nameCategory AS category, jobOffert.location, jobOffert.salary, jobOffert.modality, user.name AS employer FROM jobOffert JOIN user ON jobOffert.cuiEmployer = user.cui JOIN categories ON jobOffert.numberCategorie = categories.numberCategory WHERE jobOffert.numberJobOffert = ? AND jobOffert.state > 0";
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

    public Optional<Job> getJobForEdit(int number){
        String query = "SELECT jobOffert.*, categories.nameCategory AS category, user.name AS employer FROM jobOffert JOIN user ON jobOffert.cuiEmployer = user.cui JOIN categories ON jobOffert.numberCategorie = categories.numberCategory WHERE jobOffert.numberJobOffert = ? AND jobOffert.state > 0";
        Job job = new Job();
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, number);
            try (var resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    job.setNumberJobOffert(number);
                    job.setNameEmployer(resultSet.getString("employer"));
                    job.setNameOfJobOffert(resultSet.getString("nameOfJobOffert"));
                    job.setDescription(resultSet.getString("description"));
                    job.setCategory(resultSet.getString("category"));
                    job.setLocation(resultSet.getString("location"));
                    job.setSalary(resultSet.getInt("salary"));
                    job.setModality(resultSet.getString("modality"));
                    job.setDetails(resultSet.getString("details"));
                    job.setPublicationDate(resultSet.getDate("publicationDate"));
                    job.setApplicationDeadline(resultSet.getDate("applicationDeadline"));
                    job.setNumberCategorie(resultSet.getInt("numberCategorie"));
                    return Optional.ofNullable(job);
                }
            }
        }catch (SQLException e){
            System.out.println("Error in getJobForEdit please check: " + e);
        }
        return Optional.empty();
    }

    public List<Job> publishedWorksByCui(int cui){
        String query = " SELECT jo.numberJobOffert, u.name AS userName, jo.nameOfJobOffert AS jobName, jo.description, jo.modality, jo.salary, jo.publicationDate, jo.details, jo.cuiEmployer, jo.state, c.nameCategory AS categories FROM jobOffert jo INNER JOIN user u ON jo.cuiEmployer = u.cui LEFT JOIN categories c ON jo.numberCategorie = c.numberCategory WHERE jo.cuiEmployer = ? AND jo.state > 0";
        List<Job> jobOffers = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, cui);
            try(var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Job job = Job.builder()
                            .cuiEmployer(resultSet.getInt("cuiEmployer"))
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

    public boolean updateStateJob(int numberJobOffert){
        String query = "UPDATE jobOffert SET state = 0 WHERE numberJobOffert = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, numberJobOffert);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println("Error om update state job please check: " + e );
        }
        return false;
    }

    public boolean updateJobOffertByNumberJobOffert(Job job){
        return false;
    }

    public boolean insertNewJobOffert(Job job){
        String query = "INSERT INTO jobOffert (nameOfJobOffert, description, location, details, publicationDate, applicationDeadline, state, salary, cuiEmployer, modality, numberCategorie, selectedUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null)";
        if (job != null ){
            try (var preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, job.getNameOfJobOffert());
                preparedStatement.setString(2, job.getDescription());
                preparedStatement.setString(3, job.getLocation());
                preparedStatement.setString(4, job.getDetails());
                preparedStatement.setDate(5, job.getPublicationDate());
                preparedStatement.setDate(6, job.getApplicationDeadline());
                preparedStatement.setInt(7, 1);
                preparedStatement.setDouble(8, job.getSalary());
                preparedStatement.setInt(9, job.getCuiEmployer());
                preparedStatement.setString(10, job.getModality());
                preparedStatement.setInt(11, job.getNumberCategorie());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e){
                System.out.println("Error in insert new job offert: " + e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateJobOffert(Job job) {
        String query = "UPDATE jobOffert SET nameOfJobOffert = ?, description = ?, location = ?, details = ?, publicationDate = ?, applicationDeadline = ?, state = ?, salary = ?, cuiEmployer = ?, modality = ?, numberCategorie = ? WHERE numberJobOffert = ?";

        if (job != null) {
            try (var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, job.getNameOfJobOffert());
                preparedStatement.setString(2, job.getDescription());
                preparedStatement.setString(3, job.getLocation());
                preparedStatement.setString(4, job.getDetails());
                preparedStatement.setDate(5, job.getPublicationDate());
                preparedStatement.setDate(6, job.getApplicationDeadline());
                preparedStatement.setInt(7, 1); // Assuming state is always updated to 1
                preparedStatement.setDouble(8, job.getSalary());
                preparedStatement.setInt(9, job.getCuiEmployer());
                preparedStatement.setString(10, job.getModality());
                preparedStatement.setInt(11, job.getNumberCategorie());
                preparedStatement.setInt(12, job.getNumberJobOffert()); // Assuming numberJobOffert is used for WHERE clause
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Error in update job offert: " + e);
                return false;
            }
        } else {
            return false;
        }
    }

}
