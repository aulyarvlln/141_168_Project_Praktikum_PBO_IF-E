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

public abstract class BaseRepository {
    protected DatabaseConnection dbConnection;
    
    public BaseRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    public Connection getConnection() {
        return dbConnection.getConnection();
    }
    
    // Abstract method untuk validasi sebelum operasi
    protected abstract boolean validateData(Object entity);
    
    // Template method pattern
    public final boolean executeWithValidation(Object entity, OperationType type) {
        if (!validateData(entity)) {
            return false;
        }
        
        switch(type) {
            case INSERT:
                return insertEntity(entity);
            case UPDATE:
                return updateEntity(entity);
            default:
                return false;
        }
    }
    
    protected abstract boolean insertEntity(Object entity);
    protected abstract boolean updateEntity(Object entity);
    
    public enum OperationType {
        INSERT, UPDATE
    }
}
