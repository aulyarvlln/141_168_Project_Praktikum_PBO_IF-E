/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

public class EventVendor {
    public int ID;
    public int eventID;
    public int vendorID;
    private double estimatedPrice;
    private double actualPrice;
    public int qty;
    public String paymentStatus;
    public String notes;
    public String vendorName;
    public String vendorCategory;
    
    public EventVendor(){}
    
    public EventVendor(
        int eventID,
        int vendorID,
        double estimatedPrice,
        int qty,
        String paymentStatus,
        String notes
    ){
        this.eventID = eventID;
        this.vendorID = vendorID;
        this.estimatedPrice = estimatedPrice;
        this.actualPrice = 0;
        this.qty = qty;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
    }
    
    public EventVendor(
        int ID,
        int eventID,
        int vendorID,
        double estimatedPrice,
        double actualPrice,
        int qty,
        String paymentStatus,
        String notes
    ){
        this.ID = ID;
        this.eventID = eventID;
        this.vendorID = vendorID;
        this.estimatedPrice = estimatedPrice;
        this.actualPrice = actualPrice;
        this.qty = qty;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
    }
    
    public double getEstimatedPrice(){
        return estimatedPrice;
    }
    
    public double getActualPrice(){
        return actualPrice;
    }
    
    public void setEstimatedPrice (double estimatedPrice){
        if (estimatedPrice < 0) {
            //pesan eror kalau harganya mines
        }
        this.estimatedPrice = estimatedPrice;
    }
    
    public void setActualPrice (double actualPrice) {
        if ( actualPrice < 0) {
            //pesan eror kalau harganya mines
        }
        this.actualPrice = actualPrice;
    }
    
    public double selisihHarga (){
        return actualPrice - estimatedPrice;
    }
    
    public double totalHargaEstimasi (){
        return estimatedPrice * qty;
    }
    
    public double totalHargaAsli (){
        return actualPrice * qty;
    }
    
    @Override
    public String toString() {
        return vendorName + " - Rp " + estimatedPrice + " x " + qty + " (" + paymentStatus + ")";
    }
    
    
}
