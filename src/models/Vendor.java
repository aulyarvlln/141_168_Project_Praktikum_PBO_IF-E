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
<<<<<<< Updated upstream
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
        this.maxPrice = maxPrice;
        this.unitType = unitType;
=======
    //id, name, kategori, min price, max price, kontak, rating, notes
    public int ID;
    public String namaVendor;
    public String kategori;
    public int minPrice;
    public int maxPrice;
    public String contact;
    public int rating;
    public String notes;
    
    //constructor
    public Vendor(
        int ID,
        String namaVendor,
        String kategori,
        int minPrice,
        int maxPrice,
        String contact,
        int rating,
        String notes
    ){
        this.ID = ID;
        this.namaVendor = namaVendor;
        this.kategori = kategori;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
>>>>>>> Stashed changes
        this.contact = contact;
        this.rating = rating;
        this.notes = notes;
    }
<<<<<<< Updated upstream
    
//    constructor dengan id - untuk update
    public Vendor(int id, String name, String category, double minPrice, double maxPrice, String unitType, String contact, double rating, String notes) {
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
    
//    getter
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getMinPrice() { return minPrice; }
    public double getMaxPrice() { return maxPrice; }
    public String getUnitType() { return unitType; }
    public String getContact() { return contact; }
    public double getRating() { return rating; }
    public String getNotes() { return notes; }
    
//    setter
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setMinPrice(double minPrice) { this.minPrice = minPrice; }
    public void setMaxPrice(double maxPrice) { this.maxPrice = maxPrice; }
    public void setUnitType(String unitType) { this.unitType = unitType; }
    public void setContact(String contact) { this.contact = contact; }
    public void setRating(double rating) { this.rating = rating; }
    public void setNotes(String notes) { this.notes = notes; }
    
//    validasi data
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama Vendor Tidak Boleh Kosong!");
        }
        
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Harga Tidak Boleh Negatif!");
        }
        
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Harga Minimum tidak boleh lebih besar dari Harga Maksimum!");
        }
    }
    
    @Override
    public String toString() {
        return name + " (" + category + ") - Rp " + minPrice + " - Rp " + maxPrice;
    }
}
=======
}
>>>>>>> Stashed changes
