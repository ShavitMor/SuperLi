package DataLayer;

import BusinessLayer.Employee;
import BusinessLayer.EmployeeController;
import DataLayer.DTOs.ShiftWorkerDTO;
import DataLayer.DataMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftWorkerMapper extends DataMapper {

    public ShiftWorkerMapper() {
        super("ShiftWorker");
    }

    @Override
    protected ShiftWorkerDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date = rs.getDate(1);
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new ShiftWorkerDTO(this, localdate, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
    }

    public List<ShiftWorkerDTO> getAllEmployeesShifts() {
        return select().stream()
                .map(ShiftWorkerDTO.class::cast)
                .collect(Collectors.toList());
    }


    public boolean insertWorkerToShift(LocalDate lc, String shiftTime, String branch, String mid, String role) {
        return insertShiftEmployee(new ShiftWorkerDTO(this, lc, shiftTime, branch, mid, role));
    }

    //insert
    public boolean insertShiftEmployee(ShiftWorkerDTO shiftWorkerDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO ShiftWorker (date , shift_Type, branch, empId,empRole) " +
                    "VALUES (?, ?, ?, ?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(shiftWorkerDTO.shiftDate));
            stmt.setString(2, shiftWorkerDTO.shiftTime);
            stmt.setString(3, shiftWorkerDTO.branch);
            stmt.setString(4, shiftWorkerDTO.id);
            stmt.setString(5, shiftWorkerDTO.role);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Close the resources
            stmt.close();
            conn.close();

            // Check if any rows were affected by the insert
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return false;
        }
    }



    public boolean delete(LocalDate shiftDate,String shiftTime, String branch, String mid) {
        boolean success = false;
        try {
            Connection connection = DriverManager.getConnection(_connectionstring);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ShiftWorker WHERE date =? and shift_Type =? and branch=? and empId = ?");
            statement.setDate(1, java.sql.Date.valueOf(shiftDate));
            statement.setString(2, shiftTime);
            statement.setString(3, branch);
            statement.setString(4, mid);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public ArrayList<Employee> getWorkersByTypeAndShift(LocalDate date, String time, String branch, String role) {
        ArrayList<Employee> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT empId FROM ShiftWorker WHERE date = ? AND shift_Type = ? AND branch = ? and empRole=?")) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setString(3, branch);
            stmt.setString(4,role);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(EmployeeController.getInstance().getEmployee(rs.getString(1)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }


}