- Aulya Revalina | 123240141
- Azzah Fauziya Kamila | 123240168

---

## **Event Organizer**

Aplikasi untuk mengelola event, vendor, dan tugas persiapan acara.

### **Deskripsi**

Event Organizer adalah aplikasi yang membantu event organizer dalam merencanakan dan memantau progress acara. Aplikasi ini memungkinkan pengguna untuk mengelola data event, memilih vendor sesuai kategori, serta mengatur tugas persiapan sebelum acara berlangsung.

---

### **Fitur:**

#### 1. Event
- Tambah event baru (nama, tanggal, customer, budget, total tamu)
- Lihat daftar semua event
- Detail event (double klik pada tabel)
- Update status acara (Belum Selesai, Selesai)
- Update status pembayaran (Belum Bayar, Lunas)
- Hapus event (cascade ke vendor & tugas terkait)

#### 2. Vendor
- Lihat daftar vendor (25 vendor dari 5 kategori)
- Tambah vendor ke event (dengan validasi harga min-max)
- Edit harga pakai vendor (double klik pada tabel vendor)
- Hapus vendor dari event
- Total akhir event otomatis terupdate

#### 3. Tugas Persiapan
- Tambah tugas (nama tugas, vendor, deadline, status)
- Update status tugas (double klik)
- Hapus tugas

#### 4. Validasi & Error Handling
- Validasi input tidak boleh kosong
- Validasi budget dan total tamu > 0
- Validasi harga vendor antara min_price dan max_price
- Peringatan jika sisa budget tidak mencukupi
- Konfirmasi sebelum menghapus data

---

### **Pola Desain yang Digunakan**
- MVC (Model-View-Controller)
- Singleton Pattern (DatabaseConnection)
- Repository Pattern (interface Repository)
- Inheritance (BaseRepository)
- Multithreading (Loading data dengan Thread)
