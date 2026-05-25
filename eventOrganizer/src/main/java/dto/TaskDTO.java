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

public class TaskDTO {
    private int id;
    private int eventId;
    private int vendorId;
    private String namaTugas;
    private Date deadline;
    private String statusPengerjaan;
    private String vendorNama; // tambah ini
        
    public TaskDTO(
        int id, 
        int eventId, 
        int vendorId, 
        String namaTugas, 
        Date deadline, 
        String statusPengerjaan    
    ){
        this.id = id;
        this.eventId = eventId;
        this.vendorId = vendorId;
        this.namaTugas = namaTugas;
        this.deadline = deadline;
        this.statusPengerjaan = statusPengerjaan;
    }
    
    public int getId() { 
        return id; }
    
    public int getEventId() { 
        return eventId; }
    
    public int getVendorId() { 
        return vendorId; }
    
    public String getNamaTugas() { 
        return namaTugas; }
    
    public Date getDeadline() { 
        return deadline; }
    
    public String getStatusPengerjaan() { 
        return statusPengerjaan; }
    public void setStatusPengerjaan(String statusPengerjaan) { 
        this.statusPengerjaan = statusPengerjaan; }
    
    public String getVendorNama() { 
        return vendorNama; }
    public void setVendorNama(String vendorNama) { 
        this.vendorNama = vendorNama; }
}