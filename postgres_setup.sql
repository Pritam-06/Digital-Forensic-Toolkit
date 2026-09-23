CREATE DATABASE digital_forensics;

\c digital_forensics;

CREATE TABLE IF NOT EXISTS forensic_reports (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    file_size BIGINT NOT NULL,
    readable BOOLEAN NOT NULL,
    writable BOOLEAN NOT NULL,
    hash_value VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    report_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
