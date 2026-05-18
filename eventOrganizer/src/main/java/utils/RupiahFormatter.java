/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author ACER
 */

import java.text.NumberFormat;
import java.util.Locale;

public class RupiahFormatter {
    public static String formatRp(double angka) {
        Locale indo = Locale.of("id", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indo);
        return formatter.format(angka);
    }
    
    public static String formatAngka(double angka) {
        Locale indo = Locale.of("id", "ID");
        NumberFormat formatter = NumberFormat.getNumberInstance(indo);
        return formatter.format(angka);
    }
}
