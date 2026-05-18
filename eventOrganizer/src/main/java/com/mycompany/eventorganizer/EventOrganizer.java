/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.eventorganizer;

import views.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author ACER
 */
public class EventOrganizer {
    public static void main(String[] args) {
        // Jalankan di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel ke system default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Jalankan Main Frame
            new MainFrame();
        });
    }
}
