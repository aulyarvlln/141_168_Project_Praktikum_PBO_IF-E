/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class EventVendorDTO {
    public int ID;
    public int eventID;
    public int vendorID;
    public double estimatedPrice;
    public double actualPrice;
    public int qty;
    public String paymentStatus;
    public String notes;
    public String vendorName;
    public String vendorCategory;
    
    public EventVendorDTO(
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
}
