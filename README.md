# 🔐 TraceVault – Digital Forensics Toolkit

TraceVault is a Java-based digital forensics toolkit developed to perform basic analysis of digital evidence files and store forensic reports in a PostgreSQL database.

The application provides a desktop-based graphical interface where users can select an evidence file, analyze its metadata, generate a SHA-256 hash, detect the file type, classify the evidence, assess risk, generate a report, and store the report in a database.

---

## 📌 Project Overview

Digital evidence can contain important information during cybersecurity and forensic investigations. Manually examining and documenting evidence can be time-consuming.

TraceVault provides a simple automated workflow for performing basic forensic analysis and maintaining structured forensic reports.

### Main Capabilities

- 📁 Evidence file selection
- 🔍 File metadata extraction
- 🔐 SHA-256 hash generation
- 📄 File type detection
- 🗂️ Evidence classification
- ⚠️ Risk assessment
- 📝 Forensic report generation
- 🗄️ PostgreSQL report storage
- 🔑 Administrator authentication
- 🖥️ Java Swing graphical interface
- 🧵 Multithreaded analysis components

---

# ✨ Features

## 1. Evidence Analysis

Users can select a digital evidence file from the local system and perform automated analysis.

The toolkit extracts information such as:

- File name
- File path
- File size
- Readability
- Writability
- File type
- Evidence category
- Risk level

## 2. SHA-256 Hash Generation

TraceVault generates a SHA-256 cryptographic hash for the selected evidence file.

The generated hash can be used as a reference for verifying the integrity of the analyzed file.

## 3. File Type Detection

The application analyzes the selected file and determines its file type using the implemented file analysis and signature inspection logic.

## 4. Evidence Classification

The toolkit categorizes analyzed evidence according to the classification rules implemented in the application.

## 5. Risk Assessment

TraceVault performs a basic risk assessment of the analyzed evidence and includes the result in the generated forensic report.

## 6. Forensic Report Generation

After analysis, TraceVault generates a structured report containing the information collected from the evidence file.

Reports can be saved to the PostgreSQL database for later reference.

## 7. PostgreSQL Database

The application uses PostgreSQL to store forensic reports.

The database stores information including:

| Field | Description |
|------|-------------|
| `id` | Unique report ID |
| `file_name` | Name of analyzed file |
| `file_path` | Location of analyzed file |
| `file_size` | Size of the file |
| `readable` | File readability status |
| `writable` | File writability status |
| `hash_value` | SHA-256 hash |
| `file_type` | Detected file type |
| `category` | Evidence category |
| `report_text` | Generated forensic report |
| `created_at` | Report creation timestamp |

---

# 🛠️ Technologies Used

- **Java 21**
- **Java Swing**
- **PostgreSQL**
- **JDBC**
- **SHA-256**
- **SQL**
- **Git & GitHub**
- **VS Code**

---

# 📂 Project Structure

```text
Digital-Forensic-Toolkit/
│
├── database/
│   ├── DatabaseStats.java
│   ├── DBConnection.java
│   ├── ReportRecord.java
│   └── ReportRepository.java
│
├── DemoEvidence/
│   ├── bundle.zip
│   ├── case_notes.txt
│   ├── evidence_data.json
│   ├── incident_report.html
│   ├── network_log.log
│   ├── report.csv
│   └── script.js
│
├── forensic/
│   ├── AnalysisResult.java
│   ├── EvidenceClassifier.java
│   ├── FileAnalyzer.java
│   ├── FileSignatureInspector.java
│   ├── FileTypeDetector.java
│   ├── ForensicTool.java
│   ├── HashGenerator.java
│   ├── InvalidEvidenceException.java
│   ├── MetaDataExtractor.java
│   ├── ReportGenerator.java
│   └── RiskAssessor.java
│
├── gui/
│   ├── AdminAuthGUI.java
│   ├── AdminAuthService.java
│   └── MainGUI.java
│
├── lib/
│   └── postgresql-42.7.5.jar
│
├── threads/
│   ├── HashThread.java
│   └── MetaDataThread.java
│
├── postgres_setup.sql
├── seed_demo_reports.sql
├── run_tracevault.bat
└── README.md
