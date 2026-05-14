/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class VendorDTO {
    public int id;
    public String name;
    public String category;
    public double minPrice;
    public double maxPrice;
    public String unitType;
    public String contact;
    public double rating;
    public String notes;
    
    public VendorDTO(
            int id, 
            String name, 
            String category, 
            double minPrice, 
            double maxPrice, 
            String unitType, 
            String contact, 
            double rating, 
            String notes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.unitType = unitType;
        this.contact = contact;
        this.rating = rating;
        this.notes = notes;
    }
}
