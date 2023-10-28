package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.TypeUser;
import com.example.backendguateempleos.model.User;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryUser {

    private final Connection connection;
    private final Auxiliary<User> auxiliary = new Auxiliary<>();

    public QueryUser(){
        this.connection = ConnectionDB.obtainConnection();
    }
    public Optional<User> loginUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    System.out.println("Ando aca en la query: " + resultSet.getString("password"));
                    return Optional.ofNullable(userSelect(username));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in login check please: " + e);
        }
        return Optional.empty();
    }

    public User userSelect(String username){
        String query = "SELECT * FROM user WHERE username = ?";
        User user = null;
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                   user = createUser(resultSet);
                }
            }
        }catch (SQLException e){
            System.out.println("Error in userSelect check please: " + e);
        }
        return user;
    }

    public boolean registerUser(User user){
        String passwordEncrypted = auxiliary.encrypt(user.getPassword());
        String query = "INSERT INTO user (codUser, cui, name, username, password, address, email, birthdate, typeOfUser) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (verifyUsername(user)){
            System.out.println("NO SE PUEDE REGISTRAR");
        } else {
            try (var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, maxCodUser() + 1);
                preparedStatement.setInt(2, user.getCui());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.setString(5, passwordEncrypted);
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, user.getEmail());
                preparedStatement.setDate(8, auxiliary.convertStringToSQLDate(user.getBirth()));
                preparedStatement.setInt(9, user.getTypeUser());
                preparedStatement.executeUpdate();
                System.out.println("Se registro el usuario");
                if (insertPhoneNumber(user)) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Error al registrar el usuario: " + e);
            }
        }
        return false;
    }

    public boolean insertPhoneNumber(User user){
        String query = "INSERT INTO phoneNumbers (phoneNumber, cuiUser) VALUES ( ?, ?)";

                try (var preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, user.getPhoneNumber());
                    preparedStatement.setInt(2, user.getCui());
                    preparedStatement.executeUpdate();
                    return true;
                }catch (SQLException e){
                    System.out.println("Error al insertar el phone number " + e);
                }
        return false;
    }

    public int maxCodUser(){
        String query = "SELECT MAX(codUser) AS maxCodUser FROM user";

        try (var preparedStatement = connection.prepareStatement(query)){
            try(var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return resultSet.getInt("maxCodUser");
                }
            }
        } catch (SQLException e){
            System.out.println("Error ");
        }
        return -1;
    }
      public List<User> usersForTypes(int typeUserSelected){
        String query = "SELECT * FROM user WHERE typeOfUser = ?";
        List<User> users = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, typeUserSelected);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    users.add(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in users for types please check: " + e);
        }
        return  users;
    }

    public ArrayList<Integer> userPhoneNumbers(int cuiUser){
        String query = "SELECT * FROM phoneNumbers WHERE cuiUser = ? ";
        ArrayList<Integer> numbers = new ArrayList<>();
        try (var preparedSatatement = connection.prepareStatement(query)){
            preparedSatatement.setInt(1, cuiUser);
            try (var resultSet = preparedSatatement.executeQuery()){
                while (resultSet.next()){
                    var phoneNumer = resultSet.getInt("phoneNumber");
                    numbers.add(phoneNumer);
                }            }
        }catch (SQLException e){
            System.out.println("Error in user phone numbers check: " + e);
        }
        return numbers;
    }

    public User createUser(ResultSet resultSet) throws SQLException{
        var codUser = resultSet.getInt("codUser");
        var cui = resultSet.getInt("cui");
        var name = resultSet.getString("name");
        var username = resultSet.getString("username");
        var password = resultSet.getString("password");
        var address = resultSet.getString("address");
        var email = resultSet.getString("email");
        var birthdate = resultSet.getString("birthdate");
        var typeUser = resultSet.getInt("typeOfUser");
         User user = User.builder().codUser(codUser)
                .cui(cui)
                .name(name)
                .username(username)
                .password(password)
                .address(address)
                .email(email)
                .birth(birthdate)
                .typeUser(typeUser)
                .build();
         return user;
    }

    public boolean verifyUsername(User user){
        String query = "SELECT * FROM user WHERE username = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getUsername());
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
