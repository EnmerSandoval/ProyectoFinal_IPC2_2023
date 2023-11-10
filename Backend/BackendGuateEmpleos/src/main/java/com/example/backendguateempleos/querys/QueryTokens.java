package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import com.example.backendguateempleos.model.AuxiliaryMethods;
import com.example.backendguateempleos.model.Token;
import com.example.backendguateempleos.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

public class QueryTokens {

    private final Connection connection;
    private final AuxiliaryMethods auxiliaryMethods = new AuxiliaryMethods();

    public QueryTokens() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public boolean verifyTokenCompletly(String token){
        String query = "SELECT * FROM tokens WHERE token = ?, state = ?, tokenExpirationDate = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setDate(3, java.sql.Date.valueOf(auxiliaryMethods.todayDate()));
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println("ERROR IN VERIFY COMPLETLY: " + e);
        }
        return false;
    }

    public boolean verifyTokenByState(String token){
        String query = "SELECT * FROM tokens WHERE token = ? AND state = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            preparedStatement.setBoolean(2, true);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Date verifyTokenDate(String token){
        String query = "SELECT * FROM tokens WHERE token = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                  return resultSet.getDate("tokenExpirationDate");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyExistTokenInTokens(String token){
        String query = "SELECT * FROM tokens WHERE token = ? ";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println("Error in verifyExistTokenInTokens please check: " + e);
        }
            return false;
    }

    public boolean verifyTokenWithState(User user){
        String query = "SELECT * FROM tokens WHERE cuiUser = ? AND state = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, user.getCui());
            preparedStatement.setBoolean(2, true);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println("Error in verify Token with state please check");
            e.printStackTrace();
        }
        return false;
    }


    public boolean verifyExistTokenInTokensHistory(String token){
        String query = "SELECT * FROM tokensHistory WHERE token = ?";
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            try (var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return true;
                }
            }

        }catch (SQLException e){
            System.out.println("ERROR in Verify Exist Token in tokens history: " + e);
        }
        return false;
    }

    public boolean insertToken(User user, String token){
        String query = "INSERT INTO tokens (cuiUser, token, state, tokenExpirationDate) values ( ?, ?, ?, ?)";
        Date date = java.sql.Date.valueOf(auxiliaryMethods.nextDay());
        try (var preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, user.getCui());
                preparedStatement.setString(2, token);
                preparedStatement.setBoolean(3, true);
                preparedStatement.setDate(4, date);
                preparedStatement.executeUpdate();
                insertHistoryToken(user, token, date);
                return true;
        }catch (SQLException e){
            System.out.println("Error in insert Token please check: " + e);
        }
        return false;
    }

    public boolean updateToken(User user, String token){
        String query = "UPDATE tokens SET token = ?, state = ?, tokenExpirationDate = ? WHERE cuiUser = ?";
        Date nextDay = java.sql.Date.valueOf(auxiliaryMethods.nextDay());
        try (var preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, token);
                preparedStatement.setBoolean(2, true);
                preparedStatement.setDate(3, nextDay);
                preparedStatement.setInt(4, user.getCui());
                preparedStatement.executeUpdate();
                insertHistoryToken(user, token, nextDay);
                return true;
        } catch (SQLException e){
            System.out.println("Error in update Token please check: " + e);
        }
        return false;
    }

    public void insertHistoryToken(User user, String token, Date date){
        String query = "INSERT INTO tokensHistory (token, cuiUser, tokenExpirationDate) VALUES (?, ?, ?)";
        try (var preparedStatemen = connection.prepareStatement(query)){
            preparedStatemen.setString(1, token);
            preparedStatemen.setInt(2, user.getCui());
            preparedStatemen.setDate(3, date);
            preparedStatemen.executeUpdate();
        } catch (SQLException e){
            System.out.println("ERROR IN INSERT HISTORY TOKEN CHECK PLEASE: " + e);
        }
    }

    public boolean switchState(String token){
        String query = "UPDATE tokens SET state = ? WHERE token = ?";
        try(var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, token);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Token> verifyTokenEntered(String token){
        String query = "SELECT * FROM tokens WHERE token = ?";
        Token tokenReturn = new Token();
        try (var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, token);
            try(var resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var tokenExpirationDate = resultSet.getDate("tokenExpirationDate");
                    var state = resultSet.getBoolean("state");
                    var cuiUser = resultSet.getInt("cuiUser");
                    tokenReturn = Token.builder().cuiUser(cuiUser)
                            .tokenExpirationDate(tokenExpirationDate)
                            .state(state).build();
                }
            }
        }catch (SQLException e){
            System.out.println("ERROR IN VERIFY TOKEN ENTERED PLEASE CHECK: " + e);
        }
        return Optional.ofNullable(tokenReturn);
    }

}
