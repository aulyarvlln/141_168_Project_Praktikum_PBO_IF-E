/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class PreparationTask {
    //id, vendor id, event id, task name, deadline, status, notes
    public int ID;
    public int eventID;
    public String taskName;
    public String deadline;
    public String status;
    public String notes;
    
    //constructor
    public PreparationTask(
        int ID,
        int eventID,
        String taskName,
        String deadline,
        String status,
        String notes
    ){
        this.ID = ID;
        this.eventID = eventID;
        this.taskName = taskName;
        this.deadline = deadline;
        this.status = status;
        this.notes = notes;
    }
}
