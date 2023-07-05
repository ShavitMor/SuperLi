package DataLayer;

import BusinessLayer.Employee;
import BusinessLayer.EmployeeController;
import BusinessLayer.EmployeeType;
import DataLayer.DTOs.RolesNeededDTO;
import DataLayer.DTOs.ShiftDTO;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RolesNeededMapper extends DataMapper {
    public RolesNeededMapper() {
        super("rolesNeeded");
    }
//get all the tafkidim and amount of specific shift.
    @Override
    protected RolesNeededDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        LocalDate date= rs.getDate(1).toLocalDate();
        String shiftType=rs.getString(2);
        String branch=rs.getString(3);
        String type = rs.getString(4);
        int amount = rs.getInt(5);
        return new RolesNeededDTO(this,date,shiftType,branch, type, amount);
    }



    // select
    public List<RolesNeededDTO> getAllRolesNeeded() {
        return select().stream()
                .map(RolesNeededDTO.class::cast)
                .collect(Collectors.toList());
    }

    // update
    public boolean updateRolesNeeded(LocalDate date,String shiftType,String branch,String role ,String fieldName, int value) {
        return update(date, shiftType, branch,role,fieldName,value);
    }

    public boolean updateRolesNeeded(LocalDate date,String shiftType,String branch, String fieldName, String value) {
        return update(date, shiftType, branch,fieldName,value);
    }


    // insert
    public boolean insertRolesNeeded(LocalDate date,String shiftType,String branch,String employeeType, int amount) {
        return insert(new RolesNeededDTO(this,date,shiftType,branch,employeeType,amount));
    }

    public boolean insert(RolesNeededDTO rolesNeededDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO rolesNeeded (date, shift_type, branch, employee_type, amount) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(rolesNeededDTO.date));
            stmt.setString(2, rolesNeededDTO.shiftType);
            stmt.setString(3, rolesNeededDTO.branch);
            stmt.setString(4, rolesNeededDTO.employeeType);
            stmt.setInt(5, rolesNeededDTO.amount);

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


    public boolean update(LocalDate date, String shiftType, String branch,String role ,String attributeName, int attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update rolesNeeded"  + " set " + attributeName + "=? where date=? and shift_Type=? and branch=? and employee_type=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                statement.setString(5, role);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    // delete
    public boolean removeRolesNeeded(RolesNeededDTO rolesNeededDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM rolesNeeded WHERE date = ? AND shift_type = ? AND branch = ? AND employee_type = ? AND amount = ?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(rolesNeededDTO.date));
            stmt.setString(2, rolesNeededDTO.shiftType);
            stmt.setString(3, rolesNeededDTO.branch);
            stmt.setString(4, rolesNeededDTO.employeeType);
            stmt.setInt(5, rolesNeededDTO.amount);


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




    public HashMap<String, Integer> getRolesNeeded(LocalDate date, String time, String branch) {
        HashMap<String, Integer> results = new HashMap();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT employee_type,amount FROM RolesNeeded WHERE date = ? AND shift_type = ? AND branch = ?")) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setString(3, branch);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.put(rs.getString(1),rs.getInt(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }

}
