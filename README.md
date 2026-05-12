# 🌊 Oceana

Platform edukasi kelautan berbasis mobile yang dirancang untuk meningkatkan minat masyarakat Indonesia terhadap laut, sehingga generasi penerus dapat memanfaatkan dan melestarikan sumber daya laut secara berkelanjutan.

---

## 📌 Tentang Proyek

Oceana adalah aplikasi mobile yang dikembangkan oleh tim **OSORA** dari Universitas Amikom Yogyakarta sebagai bagian dari kompetisi **UNITY #11 Competitions 2023** kategori Software Development yang menghadirkan solusi inovatif dalam bidang edukasi kelautan.

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

### 📱 Dashboard
Halaman utama yang menampilkan antarmuka interaktif dengan navigasi ke semua fitur aplikasi.

### 🔐 Authentication & Profile
Sistem login/register yang aman serta halaman profil pengguna untuk personalisasi pengalaman.

---

## 🖥️ Preview Aplikasi

Dashboard Oceana menampilkan antarmuka interaktif bertema bawah laut dengan elemen visual 3D yang imersif. Navigasi utama mencakup:

- **Beranda** — Halaman utama dengan visual undersea world
- **Materi** — Konten edukasi kelautan (Marine Life & Atlantis)
- **Profil** — Informasi pengguna dan pengaturan
- **Tentang** — Informasi platform

---

## 🔀 Struktur Branch

Proyek Oceana menggunakan model Git Flow dengan struktur branch berikut:

### Branch Utama
- **`main`** — Branch produksi (stabil, siap rilis)
- **`develop`** — Branch pengembangan utama (integrasi semua fitur)

### Feature Branches (Per Anggota Tim)
- **`feature/dashboard`** — Pengembangan halaman dashboard utama
- **`feature/marine-life`** — Pengembangan fitur Marine Life
- **`feature/atlantis`** — Pengembangan fitur Atlantis (destinasi wisata)
- **`feature/auth`** — Pengembangan sistem login dan register
- **`feature/profile`** — Pengembangan halaman profil pengguna

---

## 📋 Pembagian Tugas (MVP - Minimum Viable Product)

| Anggota | Fitur/Bagian | Branch | Status |
|---------|------------|--------|--------|
| **Bagas** | 🐠 Marine Life | `feature/marine-life` | MVP |
| **Abi** | 🏛️ Atlantis | `feature/atlantis` | MVP |
| **Kensya** | 📱 Dashboard | `feature/dashboard` | MVP |
| **Thoriq** | 🔐 Auth & Register | `feature/auth` | MVP |
| **Thoriq** | 👤 Profile | `feature/profile` | Post-MVP |

---

## 🛠️ Panduan Kontribusi (untuk Anggota Tim)

### Prasyarat

Pastikan sudah terinstall:
- [Git](https://git-scm.com/downloads) — cek dengan `git --version` di terminal
- Android Studio (versi terbaru [kalau bisa])
- JDK 11 atau lebih tinggi

---

### Langkah 1 — Clone Repo

Buka terminal (Command Prompt atau PowerShell di Windows), lalu jalankan:

```bash
git clone https://github.com/unlikeneptunev/oceana.git
cd oceana
```

---

### Langkah 2 — Masuk ke Branch Masing-masing

Jalankan sesuai dengan tugas yang telah ditentukan:

```bash
# Keke (Dashboard)
git checkout feature/dashboard

# Bagas (Marine Life)
git checkout feature/marine-life

# Abi (Atlantis)
git checkout feature/atlantis

# Thoriq (Auth & Register)
git checkout feature/auth

# Thoriq (Profile)
git checkout feature/profile
```

Verifikasi kamu sudah di branch yang benar:

```bash
git branch
```

Branch aktif ditandai dengan tanda bintang `*`.

---

### Langkah 3 — Buka Project di Android Studio

Buka Android Studio, pilih **File > Open**, lalu pilih folder `oceana` hasil clone tadi. Tunggu Gradle sync selesai.

---

### Langkah 4 — Mulai Coding

Kerjakan fitur masing-masing di Android Studio seperti biasa. **Pastikan mengikuti convention yang sudah disepakati tim.**

**Catatan khusus per anggota:**
- **Keke:** Kode dashboard yang sudah dibuat sebelumnya dipindahkan manual ke folder project ini, jangan lanjut di folder lama.
- **Semua:** Pastikan testing dilakukan secara berkala sebelum push.

---

### Langkah 5 — Simpan Progress ke GitHub

Setiap kali selesai mengerjakan satu bagian kecil dan kode bisa di-run tanpa error, jalankan:

```bash
git add .
git commit -m "feat: deskripsi singkat apa yang dikerjakan"
git push origin feature/nama-branch-kamu
```

**Contoh pesan commit yang benar:**

```
feat: add dashboard UI layout
feat: add marine life list screen with data binding
fix: fix login button not responding
refactor: improve code structure in auth module
docs: update documentation for marine life API
```

**Format Commit Message:**
- `feat:` — Fitur baru
- `fix:` — Bug fixes
- `refactor:` — Perbaikan kode tanpa mengubah fungsionalitas
- `docs:` — Perubahan dokumentasi
- `style:` — Perubahan formatting/style (whitespace, semicolon, dll)
- `test:` — Penambahan atau perbaikan tests

---

### Langkah 6 — Ambil Update Terbaru dari `develop`

Jalankan ini setiap kali mau mulai coding supaya tidak ketinggalan perubahan dari anggota lain:

```bash
git pull origin develop
```
---

### Langkah 7 — Merge ke Branch Develop (Setelah Fitur Selesai)

Ketika fitur sudah selesai dan siap di-merge:

1. **Push branch terbaru:**
   ```bash
   git push origin feature/nama-branch-kamu
   ```

2. **Kabari grup** bahwa fitur sudah siap untuk di-review

3. **Minta review** dari minimal 1 anggota tim lain sebelum merge

4. **Buat Pull Request (PR)** dari feature branch ke `develop` dengan deskripsi yang jelas:
   - Fitur apa yang ditambahkan
   - Testing yang sudah dilakukan
   - Screenshot/demo jika diperlukan

5. **Setelah PR di-approve**, lakukan merge ke `develop`

---

### Ringkasan Alur Harian

```
┌─ Mulai kerja
│  └─ git pull origin develop (ambil update terbaru)
│
├─ Selesai mengerjakan bagian kecil
│  └─ git add .
│  └─ git commit -m "feat: deskripsi"
│  └─ git push origin feature/nama-branch-kamu
│
└─ Fitur sudah lengkap
   └─ Kabari grup + minta review
   └─ Buat Pull Request ke develop
   └─ Setelah di-approve → merge ke develop
```

---

## 🔗 Referensi & Resources

- [Android Developer Docs](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Git & GitHub Guide](https://guides.github.com/)
- [MVVM Architecture Pattern](https://developer.android.com/jetpack/guide)
