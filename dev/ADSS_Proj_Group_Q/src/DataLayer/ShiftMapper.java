package DataLayer;

import DataLayer.DTOs.ShiftDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftMapper extends DataMapper {
    public ShiftMapper() {
        super("shifts");
    }

    @Override
    protected ShiftDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate(1).toLocalDate();
        LocalTime begin = rs.getTime(2).toLocalTime();
        int shiftTime = rs.getInt(3);
        LocalTime end = rs.getTime(4).toLocalTime();
        String shiftType =rs.getString(5);
        String branch = rs.getString(6);
        String shiftManagerID = rs.getString(7);
        boolean isPublished = rs.getBoolean(8);
        return new ShiftDTO(this, date, begin, shiftTime, end, shiftType, branch, shiftManagerID, isPublished);
    }

    // select
    public List<ShiftDTO> getAllShifts() {
        return select().stream()
                .map(ShiftDTO.class::cast)
                .collect(Collectors.toList());
    }

    // update
    public boolean updateShift(LocalDate date,String shiftType,String branch, String fieldName, String value) {
        return update(date,shiftType,branch, fieldName, value);
    }

    public boolean updateShift(LocalDate date,String shiftType,String branch, String fieldName, int value) {
        return update(date,shiftType,branch, fieldName, value);
    }

    public boolean updateShift(LocalDate date,String shiftType,String branch, String fieldName, LocalTime value) {
        return update(date,shiftType,branch, fieldName, value);
    }

    public boolean updateShift(LocalDate date,String shiftType,String branch, String fieldName, boolean value) {
        return update(date,shiftType,branch, fieldName, value);
    }
    public boolean updateShift(LocalDate date,String shiftType,String branch, String fieldName, LocalDate value) {
        return update(date,shiftType,branch, fieldName, value);
    }


    // insert
    public boolean insertShift(LocalDate date, LocalTime begin, int shiftTime, LocalTime end, String shiftType, String branch, String shiftManagerID, boolean isPublished) {
        return insertShift(new ShiftDTO(this, date, begin, shiftTime, end, shiftType, branch, shiftManagerID, isPublished));
    }


    public boolean insertShift(ShiftDTO shiftDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO shifts (date, begin, shift_time, end, shift_type, branch,shift_manager_id,is_published) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(shiftDTO.date));
            stmt.setTime(2, java.sql.Time.valueOf(shiftDTO.begin));
            stmt.setInt(3, shiftDTO.shiftTime);
            stmt.setTime(4, java.sql.Time.valueOf(shiftDTO.end));
            stmt.setString(5, shiftDTO.shiftType.substring(0,1));
            stmt.setString(6, shiftDTO.branch);
            stmt.setString(7, shiftDTO.shiftManagerID);
            stmt.setBoolean(8, shiftDTO.isPublished);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Close the resources
            stmt.close();
            conn.close();

            // Check if any rows were affected by the insert
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle any exceptions that may occur
            System.out.println(e.getMessage().toString());
            return false;
        }
    }

    //remove

    public boolean removeShift(LocalDate date, String shiftType, String branch) {
        String query = "DELETE FROM shifts WHERE date=? AND shift_type=? AND branch=?";
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            statement.setString(2, shiftType.substring(0, 1));
            statement.setString(3, branch);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            return false;
        }
    }



}
