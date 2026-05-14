/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */
public class Vendor {
    private int id;
    private String name;
    private String category;
    private double minPrice;
    private double maxPrice;
    private String unitType;
    private String contact;
    private double rating;
    private String notes;
    
//    constructor kosong
    public Vendor() {}
    
//    constructor tanpa id - untuk insert baru
    public Vendor(String name, String category, double minPrice, double maxPrice, String unitType, String contact, double rating, String notes) {
        this.name = name;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = 
    }
}
