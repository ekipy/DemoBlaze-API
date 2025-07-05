# DemoBlaze‑API & UI Automation

Ini adalah repository untuk **end‑to‑end automation testing** baik di sisi UI (Selenium + Cucumber + Allure) maupun API pada WEB [Restful-Booker]([https://restful-booker.herokuapp.com]), website demo e‑commerce.

---

## 🎯 Tujuan Proyek

- Mengotomasi pengujian fungsionalitas kunci (login, homepage dan checkout proses di UI
- Mengotomasi pengujian API pada website API Testing restful-booker
- Menghasilkan laporan hasil testing yang jelas & menarik (Allure)
- Integrasi dengan CI/CD untuk validasi otomatis & publikasi laporan
- Menampilkan hasil testing (evidence) dalam bentuk pdf
- Menggabungkan hasil testing API dan UI dalam satu report Allure

---

## 🛠 Tech Stack

| Bagian            | Teknologi/Tool                                       |
|-------------------|------------------------------------------------------|
| Backend/API       | RestAssured + JUnit5                                 |
| Frontend/UI       | Java, Selenium WebDriver, Cucumber (Gherkin), Allure |
| PDF Reporter      | iText7                                               |
| Build & CI/CD     | Gradle, GitHub Actions, Allure CLI, GitHub Pages     |

---

## 🚀 Setup & Instalasi

### Prasaratan (di runner CI):

- Java 11+ / JDK 17
- Gradle wrapper (`./gradlew`)
- Allure CLI (opsional untuk `allure open`)
- Chrome + ChromeDriver (auto‑handled via WebDriverManager)

### Prasaratan (di lokal):

- Java 11+ / JDK 17
- Install Visual Studio Code
- Install Allure CLI (opsional untuk `allure open`)

## 🧪 Menjalankan Test & Laporan Allure
### Jalankan tes + generate report + generate PDF dari lokal

```bash
git clone https://github.com/ekipy/DemoBlaze-API.git
cd DemoBlaze-API
```
- Setelah Prasaratan berhasil di install dan repo berhasil di clone ke lokal, jalankan command pada gitbash / CMD ```./merge-allure.sh``` untuk menjalankan tes dan merge report dan Generate PDF Screenshot
- Setelah berhasil menjalankan tes dan generate Report serta PDF Screenshot, jalankan command berikut pada gitbash / CMD ```allure open merge-allure-report``` untuk melihat report dalam bentuk allure website
- Screenshot PDF dapat dilihat pada 📁 : `build/reports/pdf`
- Untuk melihat hasil screenshot bentuk JPG dapat dilihat pada 📁 : `build/reports/screenshots`

## 🧷 Struktur Projek
```
DEMOBLAZE-API/
  ├─ APITEST/
      └─ src/
          └─ test/
              ├─ java/
              │   └─ apitest/                # API Test
  ├─ DEMOBLAZETEST/
      └─ report/
              ├─ cucumber.report.html   # report dari cucumber
      └─ src/
          └─ test/
              ├─ java/
              │   └─ demoblaze/
              │       ├─ pages/             # Page Objects
              │       ├─ runners/           # Set Runner untuk Cucumber
              │       ├─ steps/             # Step Definitions
              │       └─ util/              # Helpers (Hooks, ScreenshotUtil, ...)
              └─ resources/
                  └─ allure/                # File properti allure
                  └─ features/              # File `.feature`
build/                            # Output & logs
merged-allure-results/            # (CI output)
merged-allure-report/             # Report Allure (CI output)
build/reports/pdf/                # PDF screenshot per scenario
build/reports/screenshots/        # Screenshot PNG per step
```

## 🔄 GitHub Actions & Deployment

Workflow CI/CD secara otomatis:

1. Trigger: push ke branch main
2. Job:
   - Install JDK, setup Allure CLI
   - Jalankan skrip merge-allure.sh
   - Upload Allure report sebagai artifact
   - Deploy ke GitHub Pages di branch gh-pages
📌 Pastikan kamu punya secret GH_TOKENS untuk deploy.
👉 Setelah sukses, laporan tersedia di URL: https://<USERNAME>.github.io/<REPO>/

## ✅ Catatan Penting
- StepLogger otomatis mencatat nama step dan menyertakannya di PDF / Allure
- AlertUtil mencatat & melampirkan teks alert ke Allure
- ScreenshotFileUtil menghasilkan PDF halaman per step
- ScreenshotUtil memberikan screenshot PNG tiap step di Allure
- Jangan lupa sesuaikan nama branch/secret GH_TOKENS di CI jika bermigrasi ke repo lain

## 🛠 Troubleshooting
1. Allure CLI tidak dikenali?
   - Pastikan telah install (brew/apt atau unzip manual) dan berada di $PATH.

2. Workflow GitHub tidak jalan?
   - File .yml harus di .github/workflows/...
   - Branch sesuai (main atau ganti sesuai repo kamu)
   - Push file dummy jika baru saja merge workflow

3. Screenshot/PDF/Allure kosong?
   - Pastikan @Attachment ada di util
   - Jalankan cleanup & run ulang test

4. PDF langkah kosong/Unnamed Step?
   - Pastikan gunakan StepLogger.given/when/then(...) di semua step definition
   - Pastikan afterStep() memanggil PDF util dengan tracker step

Have fun! 😄

