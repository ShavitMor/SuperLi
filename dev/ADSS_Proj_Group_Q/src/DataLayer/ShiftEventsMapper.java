package DataLayer;

import DataLayer.DTOs.ShiftEventDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShiftEventsMapper extends DataMapper{

    public ShiftEventsMapper() {
        super("ShiftEvents");
    }

    @Override
    protected ShiftEventDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date = rs.getDate(1);
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new ShiftEventDTO(this,localdate,rs.getString(1),rs.getString(2),rs.getString(3));
    }
    //get
    public ArrayList<String> getShiftEvents(LocalDate date, String time, String branch) {
        ArrayList<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT event FROM ShiftEvents WHERE date = ? AND shiftType = ? AND branch = ?")) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setString(3, branch);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }
    //insert
    public boolean insertShiftEvent(LocalDate date,String time,String branch,String event) {
        return insertShiftEvent(new ShiftEventDTO(this, date, time,branch,event));
    }

    private boolean insertShiftEvent(ShiftEventDTO shiftEvent) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO ShiftEvents (date,shiftType,branch,event) " +
                    "VALUES (?, ?, ?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, java.sql.Date.valueOf(shiftEvent.date));
            stmt.setString(2, shiftEvent.time);

            stmt.setString(3, shiftEvent.branch);
            stmt.setString(4, shiftEvent.event);

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
}
