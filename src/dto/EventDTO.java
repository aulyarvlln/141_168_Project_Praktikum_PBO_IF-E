/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ACER
 */
public class EventDTO {
    public int id;
    public String eventName;
    public LocalDate eventDate;
    public LocalTime startTime;
    public LocalTime endTime;
    public String customerName;
    public String customerPhone;
    public double budgetCustomer;
    public String status;
    
    public EventDTO(
            int id, 
            String eventName, 
            LocalDate evenDate,
            LocalTime startTime,
            LocalTime endTime, 
            String customerName, 
            String customerPhone, 
            double budgetCustomer, 
            String status){
        this.id = id;
        this.eventName = eventName;
        this.eventDate = evenDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.budgetCustomer = budgetCustomer;
        this.status = status;
    }
}
