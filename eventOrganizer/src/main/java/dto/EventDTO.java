/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author ACER
 */

public class EventDTO {
    private int id;
    private String namaEvent;
    private Date tanggalEvent;
    private String namaCust;
    private String nomorCust;
    private double budgetCust;
    private int totalTamu;
    private String statusAcara;
    private double totalAkhirPrice;
    private String paymentStatus;
    
    public EventDTO(
        int id,
        String namaEvent,
        Date tanggalEvent,
        String namaCust,
        String nomorCust,
        double budgetCust,
        int totalTamu,
        String statusAcara,
        double totalAkhirPrice, 
        String paymentStatus
    ) {
        this.id = id;
        this.namaEvent = namaEvent;
        this.tanggalEvent = tanggalEvent;
        this.namaCust = namaCust;
        this.nomorCust = nomorCust;
        this.budgetCust = budgetCust;
        this.totalTamu = totalTamu;
        this.statusAcara = statusAcara;
        this.totalAkhirPrice = totalAkhirPrice;
        this.paymentStatus = paymentStatus;
    }
    
    public int getId() { 
        return id; }
    
    public String getNamaEvent() { 
        return namaEvent; }
    
    public Date getTanggalEvent() { 
        return tanggalEvent; }
    
    public String getNamaCust() { 
        return namaCust; }

    public String getNomorCust() { 
        return nomorCust; }
    
    public double getBudgetCust() {
        return budgetCust; }
    
    public int getTotalTamu() { 
        return totalTamu; }
    
    public String getStatusAcara() { 
        return statusAcara; }
    public void setStatusAcara(String statusAcara) { 
        this.statusAcara = statusAcara; }
    
    public double getTotalAkhirPrice() { 
        return totalAkhirPrice; }
    
    public String getPaymentStatus() { 
        return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { 
        this.paymentStatus = paymentStatus; }
}