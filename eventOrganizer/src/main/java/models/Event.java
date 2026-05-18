/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;
    private String eventName;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String customerName;
    private String customerPhone;
    private double budgetCustomer;
    private String status;
    
//    constructor kosong
    public Event() {}
    
//    contructor tanpa id
    public Event(String eventName, LocalDate evenDate, LocalTime endTime, String customerName, String customerPhone, double budgetCustomer, String status) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.budgetCustomer = budgetCustomer;
        this.status = status;
    }
    
//    constructor dengan id
    public Event(int id, String eventName, LocalDate evenDate, LocalTime endTime, String customerName, String customerPhone, double budgetCustomer, String status) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.budgetCustomer = budgetCustomer;
        this.status = status;
    }
    
//    getter
    public int getId() { return id; }
    public String getEventName() { return eventName; }
    public LocalDate getEventDate() { return eventDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public double getBudgetCustomer() { return budgetCustomer; }
    public String getStatus() { return status; }
    
//    setter
    public void setId(int id) { this.id = id; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setBudgetCustomer(double budgetCustomer) { this.budgetCustomer = budgetCustomer; }
    public void setStatus(String status) { this.status = status; }
    
//    validasi data
    public void validate() {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama acara tidak boleh kosong");
        }
        if (eventDate == null) {
            throw new IllegalArgumentException("Tanggal acara harus diisi");
        }
        if (budgetCustomer < 0) {
            throw new IllegalArgumentException("Budget tidak boleh negatif");
        }
    }
    
//    hitung durasi acara (dalam menit)
    public int getDurationInMinutes() {
        if (startTime == null || endTime == null) return 0;
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }
    
    @Override
    public String toString() {
        return eventName + " - " + eventDate + " (" + status + ")";
    }
}