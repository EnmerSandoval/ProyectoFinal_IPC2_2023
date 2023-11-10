package com.example.backendguateempleos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection connection = null;
    private static final String url = "jdbc:mysql://localhost:3306/guateEmpleos";
    private static final String user = "usuariodba";
    private static final String password = "Password123#@!";

    public static Connection obtainConnection() {
        if (connection == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión exitosa");
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error al registrar el driver de MySQL: " + e);
            }
        }
        return connection;
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar la conexión");
                e.printStackTrace();
            }
        }
    }
}
