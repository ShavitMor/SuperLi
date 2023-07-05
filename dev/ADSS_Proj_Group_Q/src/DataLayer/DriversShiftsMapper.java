package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.DriversShiftsDTO;
import DataLayer.DTOs.WorkerConstraintsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class DriversShiftsMapper extends DataMapper{
    public DriversShiftsMapper() {
        super("DriversShifts");
    }

    @Override
    protected DriversShiftsDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date = rs.getDate(1);
        LocalDate localdate = date.toLocalDate();
        return new DriversShiftsDTO(this,localdate,rs.getString(2),rs.getString(3));
    }
    //getter
    public List<DriversShiftsDTO> getAllDriverShifts() {
        return select().stream()
                .map(DriversShiftsDTO.class::cast)
                .collect(Collectors.toList());
    }
    //insert


    public boolean insert(LocalDate date, String ShiftType, String id) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO DriversShifts ( date ,shiftType,id) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, ShiftType);
            stmt.setString(3, id);

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

    //delete

    public boolean delete(LocalDate date, String time, String id) {
        boolean success = false;
        try {
            Connection connection = DriverManager.getConnection(_connectionstring);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM DriversShifts WHERE  id = ? and date =? and shiftType =? ");
            statement.setString(1, id);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().toString());
        }
        return success;
    }

}
