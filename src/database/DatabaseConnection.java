/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author ACER
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Variabel statis untuk menyimpan satu-satunya instance
    // Gunakan 'volatile' agar perubahan terlihat oleh semua thread
    
    private static volatile DatabaseConnection instance;
    private Connection connection;

    // 2. Private constructor agar tidak bisa di-instansiasi dari luar
    private DatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/event_organizer";
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Koneksi ke Database Gagal: " + e.getMessage());
        }
    }

    // 3. Method publik statis untuk mendapatkan instance (Singleton)
    public static DatabaseConnection getInstance() {
        if (instance == null) { // Cek pertama (tanpa locking)
            synchronized (DatabaseConnection.class) {
                if (instance == null) { // Cek kedua (dengan locking)
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}