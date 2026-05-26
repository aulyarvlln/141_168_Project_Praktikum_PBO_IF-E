/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author ACER
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import dto.EventDTO;
import models.Event;
import views.MainFrame;
import views.PEventDetail;
import views.DTambahEvent;

public class EventController {
    private Event eventModel;
    private MainFrame mainFrame;

    public EventController() {
        this.eventModel = new Event();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void loadAllEvents(JTable table, DefaultListModel<String> eventListModel) {
        new Thread(() -> {
            List<EventDTO> events = eventModel.getAll();

            SwingUtilities.invokeLater(() -> {
                if (table != null) {
                    refreshEventTable(table, events);
                }
                if (eventListModel != null) {
                    refreshEventListModel(eventListModel, events);
                }
            });
        }).start();
    }

    public void showAddEventDialog() {
        DTambahEvent dialog = new DTambahEvent(mainFrame, this);
        dialog.setVisible(true);
    }

    public void saveEvent(EventDTO event, DTambahEvent dialog) {
        new Thread(() -> {
            boolean success = eventModel.insert(event);

            SwingUtilities.invokeLater(() -> {
                if (success) {
                    dialog.dispose();
                    loadAllEvents(mainFrame.getEventTable(), null);
                    mainFrame.showMessage("Event berhasil ditambahkan!");
                } else {
                    mainFrame.showMessage("Gagal menambahkan event!");
                }
            });
        }).start();
    }

    public void showEventDetail(int eventId) {
        new Thread(() -> {
            EventDTO event = eventModel.getById(eventId);

            SwingUtilities.invokeLater(() -> {
                if (event != null && mainFrame != null) {
                    PEventDetail detailPanel = new PEventDetail(event, this);
                    mainFrame.showDetailPanel(detailPanel);
                } else if (mainFrame != null) {
                    mainFrame.showMessage("Event tidak ditemukan!");
                }
            });
        }).start();
    }

    public void deleteEvent(int eventId) {
        new Thread(() -> {
            boolean success = eventModel.deleteById(eventId);

            SwingUtilities.invokeLater(() -> {
                if (success && mainFrame != null) {
                    loadAllEvents(mainFrame.getEventTable(), null);
                    mainFrame.showMessage("Event berhasil dihapus!");
                    mainFrame.showMainPanel();
                } else if (mainFrame != null) {
                    mainFrame.showMessage("Gagal menghapus event!");
                }
            });
        }).start();
    }

    // Dihapus dialog popup "berhasil diupdate" agar tidak mengganggu saat user
    // ganti status acara / payment status di PEventDetail
    public void updateEvent(EventDTO event) {
        new Thread(() -> {
            eventModel.update(event);
            SwingUtilities.invokeLater(() -> {
                if (mainFrame != null) {
                    loadAllEvents(mainFrame.getEventTable(), null);
                }
            });
        }).start();
    }

    public EventDTO getEventById(int eventId) {
        return eventModel.getById(eventId);
    }
    
    public void updateTotalAkhirPrice(int eventId) {
        eventModel.updateTotalAkhirPrice(eventId);
    }

    public void refreshEventTable(JTable table, List<EventDTO> events) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (EventDTO e : events) {
            model.addRow(new Object[]{
                e.getId(),
                e.getNamaEvent(),
                e.getNamaCust(),
                e.getTanggalEvent(),
                e.getStatusAcara(),
                formatRupiah(e.getTotalAkhirPrice())
            });
        }
    }

    private void refreshEventListModel(DefaultListModel<String> model, List<EventDTO> events) {
        model.clear();
        for (EventDTO e : events) {
            model.addElement(e.getId() + " - " + e.getNamaEvent() + " (" + e.getNamaCust() + ")");
        }
    }
    
    private String formatRupiah(double value) {
        return String.format("Rp %,.0f", value).replace(",", ".");
    }
}