/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class VendorDTO {
    private int id;
    private String nama;
    private String kategori;
    private String kontak;
    private double minPrice;
    private double maxPrice;
    
    public VendorDTO() {}
    
    public VendorDTO(
        int id, 
        String nama, 
        String kategori, 
        String kontak, 
        double minPrice, 
        double maxPrice) {
            this.id = id;
            this.nama = nama;
            this.kategori = kategori;
            this.kontak = kontak;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
    }
    
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getNama() { 
        return nama; 
    }
    
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    
    public String getKategori() { 
        return kategori; 
    }
    
    public void setKategori(String kategori) { 
        this.kategori = kategori; 
    }
    
    public String getKontak() { 
        return kontak; 
    }
    
    public void setKontak(String kontak) { 
        this.kontak = kontak; 
    }
    
    public double getMinPrice() { 
        return minPrice; 
    }
    
    public void setMinPrice(double minPrice) { 
        this.minPrice = minPrice; 
    }
    
    public double getMaxPrice() { 
        return maxPrice; 
    }
    
    public void setMaxPrice(double maxPrice) { 
        this.maxPrice = maxPrice; 
    }
}
