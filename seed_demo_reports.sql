INSERT INTO forensic_reports
(file_name, file_path, file_size, readable, writable, hash_value, file_type, category, report_text)
VALUES
('case_notes.txt', 'D:\DigitalForenseicsToolkit\DemoEvidence\case_notes.txt', 63, true, true, 'A1C4E781', 'Document', 'Textual Evidence', 'Demo report for text-based evidence.'),
('incident_report.html', 'D:\DigitalForenseicsToolkit\DemoEvidence\incident_report.html', 87, true, true, 'D8F2074A', 'Document', 'Textual Evidence', 'Demo report for HTML evidence.'),
('bundle.zip', 'D:\DigitalForenseicsToolkit\DemoEvidence\bundle.zip', 347, true, true, '8CDE100F', 'Archive', 'Compressed Evidence', 'Demo report for compressed evidence.'),
('notepad.exe', 'C:\Windows\System32\notepad.exe', 360448, true, true, 'E0CD7E05', 'Executable', 'Program Artifact', 'Demo report for executable evidence.');
