/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author ACER
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    // localdate jadi dd MMMM yyyy > "18 Mei 2026"
    public static String formatTanggal(LocalDate tanggal) {
        if (tanggal == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return tanggal.format(formatter);
    }
    
    // localdate ke dd/MM/yyyy > "18/05/2026" (utk tabel)
    public static String formatTanggalTabel(LocalDate tanggal) {
        if (tanggal == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return tanggal.format(formatter);
    }
    
    // localtime ke HH:mm > "19:00"
    public static String formatWaktu(LocalTime waktu) {
        if (waktu == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return waktu.format(formatter);
    }
    
    // localdatetime ke dd MMM yyyy HH:mm > "18 Mei 2026 19:00"
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
