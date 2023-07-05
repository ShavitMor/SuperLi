package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.EmployeeRolesDTO;
import DataLayer.DTOs.IdAndPasswordsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRolesMapper extends DataMapper{

    public EmployeeRolesMapper() {
        super("EmployeeRoles");
    }

    @Override
    protected EmployeeRolesDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new EmployeeRolesDTO(this,rs.getString(0),rs.getString(1));
    }
    //get
    public List<String> getEmployeeRoles(String id) {
        List<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT role FROM EmployeeRoles WHERE empID =?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }
    public List<String> gethumanResurceId() {
        List<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT empId FROM EmployeeRoles WHERE role ='hr'")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }
    public List<String> getshipmentManagerId() {
        List<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT empId FROM EmployeeRoles WHERE role ='sMan'")) {
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
    public boolean addRoleToEmp(String id,String role){
        return addRoleToEmp(new EmployeeRolesDTO(this,id,role));
    }
    private boolean addRoleToEmp(EmployeeRolesDTO employeeRolesDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO EmployeeRoles (empId , role) " +
                    "VALUES (?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, employeeRolesDTO.id);
            stmt.setString(2, employeeRolesDTO.role);


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