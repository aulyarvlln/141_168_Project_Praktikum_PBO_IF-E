/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */
import java.time.LocalTime;

public class EventSchedule {
    private int id;
    private int eventId;
    private LocalTime startTime;
    private int duration; // dalam menit
    private String activityName;
    private String pic; // Person In Charge
    private String notes;
    
    private LocalTime endTime; // dihitung dari startTime + duration
    private String eventName;
    
//    constructor kosong
    public EventSchedule() {}
    
//    constructor tanpa id
    public EventSchedule(int eventId, LocalTime startTime, int duration, String activityName, String pic, String notes) {
        this.eventId = eventId;
        this.startTime = startTime;
        this.duration = duration;
        this.activityName = activityName;
        this.pic = pic;
        this.notes = notes;
    }
    
//    constructor dengan id
    public EventSchedule(int id, int eventId, LocalTime startTime, int duration, String activityName, String pic, String notes) {
        this.id = id;
        this.eventId = eventId;
        this.startTime = startTime;
        this.duration = duration;
        this.activityName = activityName;
        this.pic = pic;
        this.notes = notes;
    }
    
//    hitung endtime
    public LocalTime getEndTime() {
        if (startTime == null) return null;
        return startTime.plusMinutes(duration);
    }
    
//    getter
    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public LocalTime getStartTime() { return startTime; }
    public int getDuration() { return duration; }
    public String getActivityName() { return activityName; }
    public String getPic() { return pic; }
    public String getNotes() { return notes; }
    public String getEventName() { return eventName; }
    
//    setter
    public void setId(int id) { this.id = id; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public void setPic(String pic) { this.pic = pic; }
    public void setNotes(String notes) { this.notes = notes; }
    
//    mendapatkan range waktu
    public String getTimeRange() {
        if (startTime == null) return "";
        return startTime.toString() + " - " + getEndTime().toString();
    }
    
//    validasi data
    public void validate() {
        if (eventId <= 0) {
            throw new IllegalArgumentException("Event ID tidak valid");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Jam mulai harus diisi");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Durasi harus lebih dari 0 menit");
        }
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kegiatan tidak boleh kosong");
        }
    }
    
    @Override
    public String toString() {
        return getTimeRange() + " | " + activityName + " (" + pic + ")";
    }
}
