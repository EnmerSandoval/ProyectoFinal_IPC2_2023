package com.example.backendguateempleos.querys;

import com.example.backendguateempleos.db.ConnectionDB;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class QueryFiles {

    private final Connection connection;

    public QueryFiles() {
        this.connection = ConnectionDB.obtainConnection();
    }

    public boolean insertFileCv(int cui, Part filePart) {
        String query = "INSERT INTO applicant (cuiApplicant, cv) VALUES (?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cui);
            preparedStatement.setBlob(2, filePart.getInputStream());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in insert file CV please check: " + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
