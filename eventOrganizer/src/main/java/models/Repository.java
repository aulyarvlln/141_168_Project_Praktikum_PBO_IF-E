/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import java.util.List;

public interface Repository<T> {
    List<T> getAll(); // ambil semua data
    T getById(int id); // ambil satu data berdasarkan ID
    Boolean insert(T entity); // tambah data baru
    Boolean update(T entity); // update data
    Boolean deleteById(int id); // hapus data
}