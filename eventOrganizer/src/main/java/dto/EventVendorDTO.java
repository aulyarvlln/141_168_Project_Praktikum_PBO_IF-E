/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class EventVendorDTO {
    private int id;
    private int eventId;
    private int vendorId;
    private double hargaPakai;
    private String vendorNama;
    private String vendorKategori; // tambah ini

    public EventVendorDTO() {}

    public EventVendorDTO(int id, int eventId, int vendorId, double hargaPakai) {
        this.id = id;
        this.eventId = eventId;
        this.vendorId = vendorId;
        this.hargaPakai = hargaPakai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getVendorId() { return vendorId; }
    public void setVendorId(int vendorId) { this.vendorId = vendorId; }

    public double getHargaPakai() { return hargaPakai; }
    public void setHargaPakai(double hargaPakai) { this.hargaPakai = hargaPakai; }

    public String getVendorNama() { return vendorNama; }
    public void setVendorNama(String vendorNama) { this.vendorNama = vendorNama; }

    public String getVendorKategori() { return vendorKategori; }
    public void setVendorKategori(String vendorKategori) { this.vendorKategori = vendorKategori; }
}