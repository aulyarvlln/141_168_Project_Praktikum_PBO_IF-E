/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dto.TaskDTO;
import dto.TaskDTO;
import models.PreparationTask;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author ACER
 */
public class TaskController {
    private final PreparationTask taskModel;
    
    public TaskController() {
        this.taskModel = new PreparationTask();
    }
    
    // MULTITHREADING: Load tasks di thread terpisah
    public void loadTasks(int eventId, JTable table) {
        new Thread(() -> {
            List<TaskDTO> tasks = taskModel.getByEventId(eventId);
            
            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                
                for (TaskDTO t : tasks) {
                    String vendorName = t.getVendorNama() != null ? t.getVendorNama() : "-";
                    model.addRow(new Object[]{
                        t.getId(),
                        t.getNamaTugas(),
                        vendorName,
                        t.getDeadline(),
                        t.getStatusPengerjaan()
                    });
                }
            });
        }).start();
    }
    
    public void addTask(int eventId, String namaTugas, Integer vendorId, Date deadline, 
                        String status, JTable table) {
        new Thread(() -> {
            TaskDTO task = new TaskDTO();
            task.setEventId(eventId);
            task.setNamaTugas(namaTugas);
            task.setVendorId(vendorId);
            task.setDeadline(deadline);
            task.setStatusPengerjaan(status);
            
            boolean success = taskModel.insert(task);
            
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    loadTasks(eventId, table);
                    JOptionPane.showMessageDialog(null, "Tugas berhasil ditambahkan!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan tugas!");
                }
            });
        }).start();
    }
    
    public void updateTaskStatus(int taskId, String newStatus, int eventId, JTable table) {
        new Thread(() -> {
            TaskDTO task = taskModel.getById(taskId);
            if (task != null) {
                task.setStatusPengerjaan(newStatus);
                boolean success = taskModel.update(task);
                
                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        loadTasks(eventId, table);
                        JOptionPane.showMessageDialog(null, "Status tugas berhasil diupdate!");
                    }
                });
            }
        }).start();
    }
    
    public void deleteTask(int taskId, int eventId, JTable table) {
        new Thread(() -> {
            boolean success = taskModel.deleteById(taskId);
            
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    loadTasks(eventId, table);
                    JOptionPane.showMessageDialog(null, "Tugas berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus tugas!");
                }
            });
        }).start();
    }
}
