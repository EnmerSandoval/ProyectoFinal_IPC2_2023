package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryAdministrator {
    private final Connection connection;

    public QueryAdministrator() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public List<Category> getAllCategories(){
        String query = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        try (var preparedStatement = connection.prepareStatement(query)){
            try (var resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    var numberCategory = resultSet.getInt("numberCategory");
                    var nameCategory = resultSet.getString("nameCategory");
                    var description = resultSet.getString("description");
                    category = category.builder().numberCategory(numberCategory).nameCategory(nameCategory).description(description).build();
                    categories.add(category);
                }
            }
        } catch (SQLException e){
            System.out.println("Error in get all categories: " + e);
        }
        return categories;
    }

    public Optional<Category> getCategoryByNumberCategory(Category category){
        String query = "SELECT * FROM categories WHERE = ?";
        Category category1 = new Category();
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, category.getNumberCategory());
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var numberCategory = resultSet.getInt("numberCategory");
                    var nameCategory = resultSet.getString("nameCategory");
                    var description = resultSet.getString("description");
                    category1 = category.builder().numberCategory(numberCategory).nameCategory(nameCategory).description(description).build();
                    return Optional.ofNullable(category1);
                }
            }
        } catch (SQLException e){
            System.out.println("Error in get Category by number category: " + e);
        }
        return Optional.empty();
    }

    public boolean updateCategory(Category category){
        String query = "UPDATE categories SET nameCategory = ?, description = ? WHERE numberCategory = ?";

        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, category.getNameCategory());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getNumberCategory());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println("Error in update category: " + e);
        }
        return false;
    }

    public boolean insertCategory(Category category){
        String query = "INSERT INTO categories(nameCategory, description) VALUES ( ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, category.getNameCategory());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println("Error in insert Category: " + e);
        }
        return false;
    }
}
