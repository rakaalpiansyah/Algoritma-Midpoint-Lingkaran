# 🎯 Midpoint Circle Algorithm - Visualizer

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Java Swing](https://img.shields.io/badge/Swing/AWT-007396?style=for-the-badge&logo=java&logoColor=white)

Repositori ini berisi implementasi algoritma **Midpoint Circle** menggunakan bahasa pemrograman **Java**. Aplikasi ini tidak hanya menggambar lingkaran berdasarkan perhitungan piksel matematis, tetapi juga menyediakan antarmuka grafis (GUI) interaktif bergaya modern.

Proyek ini dikembangkan untuk memenuhi **Tugas Kelompok** mata kuliah **Grafika Komputer**.

## ✨ Fitur Utama

- ✅ **Input Dinamis:** Pengguna dapat memasukkan koordinat Titik Pusat `(xc, yc)` dan Radius `(r)` secara bebas melalui panel input.
- ✅ **Interactive Panning (Draggable Canvas):** Kanvas gambar dapat digeser ke segala arah menggunakan *mouse drag* (tahan klik kiri dan geser) layaknya peta interaktif.
- ✅ **Sistem Grid Kartesius:** Dilengkapi dengan latar belakang *grid* (garis kotak-kotak) dan sumbu X/Y utama untuk memvalidasi posisi titik pusat secara visual.
- ✅ **Live Coordinate Logging:** Otomatis mencatat dan menampilkan titik koordinat `(x, y)` untuk Oktan 1 secara *real-time* di panel sisi kanan.
- ✅ **Modern UI/UX:** Antarmuka pengguna mengadopsi tema *Dark Mode* yang elegan dan responsif.

## 📸 Screenshots

![Tampilan Utama Aplikasi](lib/images/image.png)
> *Tampilan antarmuka utama dengan grid dan panel log koordinat.*

![Fitur Panning](lib/images/image2.png)
> *Demonstrasi kanvas yang dapat digeser untuk melihat lingkaran dengan radius besar di luar layar.*

## 🚀 Cara Menjalankan Program

Program ini ditulis menggunakan *library* standar bawaan Java (`javax.swing` dan `java.awt`), sehingga **tidak memerlukan library eksternal** tambahan.

### Prasyarat
- Java Development Kit (JDK) minimal versi 8 terinstal di komputermu.

### Langkah-langkah:
1. *Clone* repositori ini atau *download* sebagai ZIP.
2. Buka terminal/Command Prompt dan arahkan ke folder tempat file disimpan.
3. *Compile* kode Java:
   ```bash


🧠 Cara Kerja Algoritma Midpoint LingkaranAlgoritma Midpoint digunakan untuk menentukan titik piksel terdekat dengan keliling lingkaran yang sebenarnya pada layar raster (monitor). Keunggulan utama algoritma ini adalah menghindari perhitungan floating-point (bilangan desimal) dan perkalian/pembagian yang berat, melainkan hanya menggunakan operasi penjumlahan dan pengurangan integer yang sangat cepat diproses oleh komputer.Langkah-langkah Matematis:Penentuan Titik Awal: Hitungan dimulai dari oktan pertama, yaitu pada posisi puncak lingkaran. Titik awalnya adalah:$x_0 = 0$$y_0 = r$Parameter Keputusan Awal ($P_0$): Digunakan untuk mengevaluasi apakah piksel selanjutnya yang harus diwarnai berada di sebelah kanan persis (Timur/E) atau di kanan bawah (Tenggara/SE).$$P_0 = 1 - r$$Iterasi (Looping): Proses perulangan dilakukan selama nilai $x < y$. Pada setiap iterasi, nilai $x$ akan selalu bertambah 1 ($x_{k+1} = x_k + 1$).Evaluasi Parameter ($P_k$):Jika $P_k < 0$: Piksel selanjutnya berada di sebelah Kanan (Timur). Koordinat $y$ tetap, dan parameter diperbarui:$$P_{k+1} = P_k + 2x_{k+1} + 1$$Jika $P_k \ge 0$: Piksel selanjutnya berada di Kanan Bawah (Tenggara). Koordinat $y$ berkurang 1 ($y_{k+1} = y_k - 1$), dan parameter diperbarui:$$P_{k+1} = P_k + 2x_{k+1} + 1 - 2y_{k+1}$$Simetri 8 Arah (Oktan): Lingkaran sangat simetris. Setelah satu titik $(x, y)$ pada oktan pertama ditemukan, algoritma akan langsung "mencetak" 7 titik cerminannya di kuadran/oktan lain dengan rumus:$(x_c + x, y_c + y)$$(x_c - x, y_c + y)$$(x_c + x, y_c - y)$$(x_c - x, y_c - y)$$(x_c + y, y_c + x)$$(x_c - y, y_c + x)$$(x_c + y, y_c - x)$$(x_c - y, y_c - x)$(Dimana $x_c$ dan $y_c$ adalah titik pusat lingkaran).
   javac MidpointCircleSmooth.java



