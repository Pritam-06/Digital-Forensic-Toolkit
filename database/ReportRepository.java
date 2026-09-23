package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportRepository {
    public void initializeDatabase() throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS forensic_reports ("
                            + "id SERIAL PRIMARY KEY, "
                            + "file_name VARCHAR(255) NOT NULL, "
                            + "file_path TEXT NOT NULL, "
                            + "file_size BIGINT NOT NULL, "
                            + "readable BOOLEAN NOT NULL, "
                            + "writable BOOLEAN NOT NULL, "
                            + "hash_value VARCHAR(255) NOT NULL, "
                            + "file_type VARCHAR(100) NOT NULL, "
                            + "category VARCHAR(100) NOT NULL, "
                            + "report_text TEXT NOT NULL, "
                            + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                            + ")"
            );
        }
    }

    public void saveReport(ReportRecord reportRecord) throws SQLException, ClassNotFoundException {
        initializeDatabase();

        String sql = "INSERT INTO forensic_reports "
                + "(file_name, file_path, file_size, readable, writable, hash_value, file_type, category, report_text) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, reportRecord.getFileName());
            preparedStatement.setString(2, reportRecord.getFilePath());
            preparedStatement.setLong(3, reportRecord.getFileSize());
            preparedStatement.setBoolean(4, reportRecord.isReadable());
            preparedStatement.setBoolean(5, reportRecord.isWritable());
            preparedStatement.setString(6, reportRecord.getHashValue());
            preparedStatement.setString(7, reportRecord.getFileType());
            preparedStatement.setString(8, reportRecord.getCategory());
            preparedStatement.setString(9, reportRecord.getReportText());
            preparedStatement.executeUpdate();
        }
    }
}
