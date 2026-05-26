-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Bulan Mei 2026 pada 16.10
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `event_organizer`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `nama_event` varchar(200) NOT NULL,
  `tanggal_event` date NOT NULL,
  `nama_cust` varchar(100) NOT NULL,
  `nomor_cust` varchar(20) NOT NULL,
  `budget_cust` decimal(15,2) NOT NULL,
  `total_tamu` int(11) NOT NULL,
  `status_acara` varchar(50) NOT NULL,
  `total_akhir_price` decimal(15,2) DEFAULT NULL,
  `payment_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `event`
--

INSERT INTO `event` (`id`, `nama_event`, `tanggal_event`, `nama_cust`, `nomor_cust`, `budget_cust`, `total_tamu`, `status_acara`, `total_akhir_price`, `payment_status`) VALUES
(2, 'Thanks Party 25', '2026-05-25', 'Angkatan 25', '0812345678', 10000000.00, 200, 'Belum Selesai', 4500000.00, 'Belum Bayar');

-- --------------------------------------------------------

--
-- Struktur dari tabel `event_vendor`
--

CREATE TABLE `event_vendor` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `harga_pakai` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `event_vendor`
--

INSERT INTO `event_vendor` (`id`, `event_id`, `vendor_id`, `harga_pakai`) VALUES
(4, 2, 24, 1500000.00),
(5, 2, 8, 3000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `preparation_task`
--

CREATE TABLE `preparation_task` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `nama_tugas` varchar(200) NOT NULL,
  `deadline` date NOT NULL,
  `status_pengerjaan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `preparation_task`
--

INSERT INTO `preparation_task` (`id`, `event_id`, `vendor_id`, `nama_tugas`, `deadline`, `status_pengerjaan`) VALUES
(2, 2, 24, 'hubungi soundsystem', '2026-05-25', 'Belum Selesai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `vendor`
--

CREATE TABLE `vendor` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `kontak` varchar(100) NOT NULL,
  `min_price` decimal(15,2) NOT NULL,
  `max_price` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `vendor`
--

INSERT INTO `vendor` (`id`, `nama`, `kategori`, `kontak`, `min_price`, `max_price`) VALUES
(1, 'Grand Ballroom Hotel Indonesia', 'Venue', '021-57891234 | grand@hotelindonesia.com', 50000000.00, 150000000.00),
(2, 'Gedung Serbaguna Senayan', 'Venue', '021-5734567 | senayan@hall.com', 35000000.00, 100000000.00),
(3, 'The Rooftop Garden Kemang', 'Venue', '081234567890 | rooftop@kemang.com', 25000000.00, 75000000.00),
(4, 'Balai Kota Convention Center', 'Venue', '021-3456789 | balai@kota.com', 40000000.00, 120000000.00),
(5, 'Villa Puncak Resort', 'Venue', '0263-512345 | villa@puncak.com', 30000000.00, 90000000.00),
(6, 'Dekorasi Mewah Indah', 'Dekorasi', '081298765432 | mewah@indah.com', 8000000.00, 35000000.00),
(7, 'Bunga dan Rias Syahrini', 'Dekorasi', '085678901234 | syahrini@dekor.com', 5000000.00, 25000000.00),
(8, 'Kreasi Dekor Art', 'Dekorasi', '087812345678 | art@kreasi.com', 3000000.00, 20000000.00),
(9, 'Pelangi Dekorasi', 'Dekorasi', '089876543210 | pelangi@dekor.com', 4000000.00, 28000000.00),
(10, 'Elegan Florist & Dekor', 'Dekorasi', '081511122233 | elegan@florist.com', 6000000.00, 30000000.00),
(11, 'PhotoVision Pro', 'Dokumentasi', '081234567891 | provision@photo.com', 5000000.00, 20000000.00),
(12, 'Memori Abadi Studio', 'Dokumentasi', '087812345679 | abadi@memori.com', 4000000.00, 18000000.00),
(13, 'Lensa Kreasi Indonesia', 'Dokumentasi', '085678901235 | lensa@kreasi.com', 3500000.00, 15000000.00),
(14, 'Visual Art Production', 'Dokumentasi', '089876543211 | visual@art.com', 6000000.00, 25000000.00),
(15, 'Capture Moment Photo', 'Dokumentasi', '081511122244 | moment@capture.com', 4500000.00, 22000000.00),
(16, 'Saji Jawa Catering', 'Catering', '021-5678901 | saji@jawa.com', 25000000.00, 75000000.00),
(17, 'Rasa Nusantara', 'Catering', '021-5678902 | nusantara@rasa.com', 20000000.00, 60000000.00),
(18, 'Prima Rasa Catering', 'Catering', '087812345680 | prima@rasa.com', 18000000.00, 55000000.00),
(19, 'Istana Kuliner', 'Catering', '085678901236 | istana@kuliner.com', 30000000.00, 80000000.00),
(20, 'Sedap Malam Catering', 'Catering', '081234567892 | sedap@malam.com', 22000000.00, 70000000.00),
(21, 'SoundPro Entertainment', 'Soundsystem', '081298765433 | pro@sound.com', 5000000.00, 20000000.00),
(22, 'Audio Makmur Jaya', 'Soundsystem', '087812345681 | makmur@audio.com', 4000000.00, 15000000.00),
(23, 'Maxx Sound System', 'Soundsystem', '085678901237 | maxx@sound.com', 6000000.00, 25000000.00),
(24, 'Gemilang Audio', 'Soundsystem', '089876543212 | gemilang@audio.com', 3500000.00, 12000000.00),
(25, 'Nusantara Sound', 'Soundsystem', '081511122255 | nusantara@sound.com', 4500000.00, 18000000.00);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `event_vendor`
--
ALTER TABLE `event_vendor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- Indeks untuk tabel `preparation_task`
--
ALTER TABLE `preparation_task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `preparation_task_ibfk_2` (`vendor_id`);

--
-- Indeks untuk tabel `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `event_vendor`
--
ALTER TABLE `event_vendor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `preparation_task`
--
ALTER TABLE `preparation_task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `vendor`
--
ALTER TABLE `vendor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `event_vendor`
--
ALTER TABLE `event_vendor`
  ADD CONSTRAINT `event_vendor_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `event_vendor_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`);

--
-- Ketidakleluasaan untuk tabel `preparation_task`
--
ALTER TABLE `preparation_task`
  ADD CONSTRAINT `preparation_task_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `preparation_task_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
