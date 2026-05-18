/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

/**
 *
 * @author ACER
 */
public class PreparationTask {
    //id, vendor id, event id, task name, deadline, status, notes
    public int ID;
    public int eventID;
    public int vendorID;
    public String taskName;
    public LocalDateTime deadline;
    public String status;
    public String notes;
    public String vendorName;
    public String eventName;
    
    public PreparationTask(){}
    
    //constructor
    public PreparationTask(
        int ID,
        int eventID,
        int vendorID,
        String taskName,
        LocalDateTime deadline,
        String status,
        String notes
    ){
        this.ID = ID;
        this.eventID = eventID;
        this.vendorID = vendorID;
        this.taskName = taskName;
        this.deadline = deadline;
        this.status = status;
        this.notes = notes;
    }
    
    public PreparationTask(
        int eventID, 
        int vendorID, 
        String taskName, 
        LocalDateTime deadline, 
        String status, 
        String notes    
    ){
        this.eventID = eventID;
        this.vendorID = vendorID;
        this.taskName = taskName;
        this.deadline = deadline;
        this.status = status;
        this.notes = notes;
    }
    
    public boolean cekBatasWaktu(){
        if (deadline == null) 
            return false;
        return LocalDateTime.now().isAfter(deadline);
    }
    
    @Override
    public String toString() {
        return taskName + " - " + deadline + " [" + status + "]";
    }
}
