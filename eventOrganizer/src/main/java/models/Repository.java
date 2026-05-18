/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author ACER
 */
public interface Repository<T> {
    List<T> getAll();
    T getById(int id);
    Boolean insert(T entity);
    Boolean update(T entity);
    Boolean deleteById(int id);
}
