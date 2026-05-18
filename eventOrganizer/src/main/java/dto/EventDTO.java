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
    
    public EventDTO() {}
    
    public EventDTO(int id, String namaEvent, Date tanggalEvent, String namaCust, 
                    String nomorCust, double budgetCust, int totalTamu, 
                    String statusAcara, double totalAkhirPrice, String paymentStatus) {
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
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNamaEvent() { return namaEvent; }
    public void setNamaEvent(String namaEvent) { this.namaEvent = namaEvent; }
    
    public Date getTanggalEvent() { return tanggalEvent; }
    public void setTanggalEvent(Date tanggalEvent) { this.tanggalEvent = tanggalEvent; }
    
    public String getNamaCust() { return namaCust; }
    public void setNamaCust(String namaCust) { this.namaCust = namaCust; }

    public String getNomorCust() { return nomorCust; }
    public void setNomorCust(String nomorCust) { this.nomorCust = nomorCust; }
    
    public double getBudgetCust() { return budgetCust; }
    public void setBudgetCust(double budgetCust) { this.budgetCust = budgetCust; }
    
    public int getTotalTamu() { return totalTamu; }
    public void setTotalTamu(int totalTamu) { this.totalTamu = totalTamu; }
    
    public String getStatusAcara() { return statusAcara; }
    public void setStatusAcara(String statusAcara) { this.statusAcara = statusAcara; }
    
    public double getTotalAkhirPrice() { return totalAkhirPrice; }
    public void setTotalAkhirPrice(double totalAkhirPrice) { this.totalAkhirPrice = totalAkhirPrice; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
