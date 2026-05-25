/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import java.sql.Connection;
import utils.DatabaseConnection;

// menyediakan kerangka dasar untuk semua repository
public abstract class BaseRepository {
    protected DatabaseConnection dbConnection;
    
    public BaseRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    public Connection getConnection() {
        return dbConnection.getConnection();
    }
}