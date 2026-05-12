# 🌊 Oceana

Platform edukasi kelautan berbasis mobile yang dirancang untuk meningkatkan minat masyarakat Indonesia terhadap laut, sehingga generasi penerus dapat memanfaatkan dan melestarikan sumber daya laut secara maksimal.

---

## 📌 Tentang Proyek

Oceana adalah aplikasi mobile yang dikembangkan oleh tim **OSORA** dari Universitas Amikom Yogyakarta sebagai bagian dari kompetisi **UNITY #11 Competitions 2023** kategori Software Development yang diselenggarakan oleh Universitas Negeri Yogyakarta (UNY).

---

## 👥 Tim Pengembang

| Nama | NIM |
|------|-----|
| Mohammad Iqbal Bagas Permana | 245150701111024 |
| Muhammad Kensya Kussyahputra H. | 245150707111047 |
| Ahmad Thoriq Hafidzurrohman | 245150701111026 |
| Muhammad Abi Abdillah | 245150701111027 |

---

## ✨ Fitur Utama

### 🐠 Marine Life
Eksplorasi dunia bawah laut Indonesia. Pengguna dapat mempelajari berbagai jenis biota laut, ekosistem, dan informasi mendalam mengenai kehidupan bawah laut Nusantara.

### 🏛️ Atlantis
Rekomendasi destinasi wisata bahari terbaik di Indonesia yang masih terjaga keasriannya. Cocok untuk pengguna yang ingin mengeksplorasi keindahan laut secara langsung.

---

## 🖥️ Preview Aplikasi

Dashboard Oseana menampilkan antarmuka interaktif bertema bawah laut dengan elemen visual 3D yang imersif. Navigasi utama mencakup:

- **Beranda** — Halaman utama dengan visual undersea world
- **Materi** — Konten edukasi kelautan
- **Tentang** — Informasi platform

---

## 🛠️ Panduan Kontribusi (untuk Anggota Tim)

### Prasyarat

Pastikan sudah terinstall:
- [Git](https://git-scm.com/downloads) — cek dengan `git --version` di terminal
- Android Studio

---

### Langkah 1 — Clone Repo

Buka terminal (Command Prompt atau PowerShell di Windows), lalu jalankan:

```bash
git clone https://github.com/unlikeneptunev/oceana.git
cd oceana
```

---

### Langkah 2 — Masuk ke Branch Masing-masing

Jalankan sesuai bagianmu:

```bash
# Keke (dashboard)
git checkout feature/dashboard

# Bagas (marine-life)
git checkout feature/marine-life

# Abi (atlantis)
git checkout feature/atlantis

# Thoriq (auth & profile)
git checkout feature/auth
```

Verifikasi kamu sudah di branch yang benar:

```bash
git branch
```

Branch aktif ditandai tanda bintang `*`.

---

### Langkah 3 — Buka Project di Android Studio

Buka Android Studio, pilih **File > Open**, lalu pilih folder `oceana` hasil clone tadi. Tunggu Gradle sync selesai.

---

### Langkah 4 — Mulai Coding

Kerjakan fitur masing-masing di Android Studio seperti biasa.

**Khusus Keke:** kode dashboard yang sudah dibuat sebelumnya dipindahkan manual ke folder project ini, jangan lanjut di folder lama.

---

### Langkah 5 — Simpan Progress ke GitHub

Setiap kali selesai mengerjakan satu bagian kecil dan kode bisa di-run tanpa error, jalankan:

```bash
git add .
git commit -m "feat: deskripsi singkat apa yang dikerjakan"
git push origin feature/nama-branch-kamu
```

Contoh pesan commit yang benar:

```
feat: add dashboard UI layout
feat: add marine life list screen
fix: fix login button not responding
```

---

### Langkah 6 — Ambil Update Terbaru dari `develop`

Jalankan ini setiap kali mau mulai coding supaya tidak ketinggalan perubahan dari anggota lain:

```bash
git pull origin develop
```

Kalau muncul konflik setelah pull, kabari dulu ke grup sebelum mencoba resolve sendiri.

---

### Ringkasan Alur Harian

```
Mulai kerja      → git pull origin develop
Selesai sebagian → git add . → git commit → git push
Fitur selesai    → kabari grup, minta di-review sebelum merge ke develop
```
