package DataLayer;

import DataLayer.DTOs.DriversNeededDTO;
import DataLayer.DTOs.ShiftDTO;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DriversNeededMapper extends DataMapper {
    public DriversNeededMapper() {
        super("drivers_needed");
    }
    //get all the tafkidim and amount of specific shift.
    @Override
    protected DriversNeededDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        LocalDate date= rs.getDate(1).toLocalDate();
        String shiftType=rs.getString(2);
        int amount = rs.getInt(3);
        return new DriversNeededDTO(this,date,shiftType, amount);
    }



    // select
    public List<DriversNeededDTO> getAllDriversNeeded() {
        return select().stream()
                .map(DriversNeededDTO.class::cast)
                .collect(Collectors.toList());
    }

    // update
    public boolean updateDriversNeeded(LocalDate date,String shiftType,String branch, String fieldName, int value) {
        return update(date, shiftType, branch,fieldName,value);
    }

    public boolean updateDriversNeeded(LocalDate date,String shiftType, int value) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "UPDATE drivers_needed SET amount=? WHERE date=? and shift_type=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, value);
                stmt.setDate(2, java.sql.Date.valueOf(date));
                stmt.setString(3,shiftType);
                res = stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    // insert
    public boolean insertDriversNeeded(LocalDate date,String shiftType, int amount) {
        return insert(new DriversNeededDTO(this,date,shiftType,amount));
    }

    public boolean insert(DriversNeededDTO driversNeededDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO drivers_needed (date, shift_type, amount) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(driversNeededDTO.date));
            stmt.setString(2, driversNeededDTO.shiftType);
            stmt.setInt(3, driversNeededDTO.amount);

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



    // delete
    public boolean removeDriversNeeded(LocalDate date,String st){
        return removeDriversNeeded(new DriversNeededDTO(this,date,st,1));
    }
    public boolean removeDriversNeeded(DriversNeededDTO driversNeededDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM drivers_needed WHERE date = ? AND shift_type = ?  AND amount = ?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(driversNeededDTO.date));
            stmt.setString(2, driversNeededDTO.shiftType);
            stmt.setInt(3, driversNeededDTO.amount);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Close the resources
            stmt.close();
            conn.close();

            // Check if any rows were affected by the delete
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle any exceptions that may occur
            System.out.println(e.getMessage().toString());
            return false;
        }
    }
}
