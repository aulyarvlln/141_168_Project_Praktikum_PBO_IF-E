/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalTime;

/**
 *
 * @author ACER
 */
public class ScheduleDTO {
    public int id;
    public int eventId;
    public LocalTime startTime;
    public int duration; // dalam menit
    public String activityName;
    public String pic; // Person In Charge
    public String notes;
    
    public ScheduleDTO (
            int id, 
            int eventId, 
            LocalTime startTime, 
            int duration, 
            String activityName, 
            String pic, 
            String notes) {
        this.id = id;
        this.eventId = eventId;
        this.startTime = startTime;
        this.duration = duration;
        this.activityName = activityName;
        this.pic = pic;
        this.notes = notes;
    }
}
