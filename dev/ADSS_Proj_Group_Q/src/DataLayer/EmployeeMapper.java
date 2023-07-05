package DataLayer;

import DataLayer.DTOs.EmployeeDTO;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper extends DataMapper  {
    public EmployeeMapper() {
        super("employees");
    }

    @Override
    protected EmployeeDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        java.sql.Date date=rs.getDate(8);
        LocalDate localdate=date.toLocalDate();
        return new EmployeeDTO(this,rs.getString(1),rs.getString(2)
                ,rs.getString(3),rs.getInt(4),
                rs.getInt(5),rs.getDouble(6),rs.getDouble(7),localdate
                ,rs.getString(9),rs.getBoolean(10),
                rs.getString(12),rs.getBoolean(11));
    }

    //select
    public List<EmployeeDTO> getAllEmployees(){
        return select().stream()
                .map(EmployeeDTO.class::cast)
                .collect(Collectors.toList());
    }


    //update
    public boolean updateEmployee(String id, String fieldName,String value){
        return update(id,fieldName,value);
    }
    public boolean updateEmployee(String id, String fieldName,int value){
        return update(id,fieldName,value);
    }
    public boolean updateEmployee(String id, String fieldName,double value){
        return update(id,fieldName,value);
    }
    public boolean updateEmployee(String id, String fieldName,LocalDate value){
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant=value.atStartOfDay().atZone(zoneId).toInstant();
        Date date=Date.from(instant);

        return update(id,fieldName, (java.sql.Date) date);
    }
    public boolean updateEmployee(String id, String fieldName,boolean value){
        return update(id,fieldName,value);
    }



    //insert
    public boolean insert(String id, String name, String bankCompany, int bankBranch, int bankId, double salary, double salaryBonus, LocalDate startDate, String terms,boolean isActive,String state,boolean isLoggedIn){
        return insertEmployee(new EmployeeDTO(this,id,name,bankCompany,bankBranch,bankId,salary,salaryBonus,startDate,terms,isActive,state,isLoggedIn));
    }
    public boolean insertEmployee(EmployeeDTO employeeDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO employees (id, name, bank_company, bank_branch, bank_id, salary, salary_bonus, start_date, terms, is_active,is_logged_in ,personal_state) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employeeDTO.id);
            stmt.setString(2, employeeDTO.name);
            stmt.setString(3, employeeDTO.bankCompany);
            stmt.setInt(4, employeeDTO.bankBranch);
            stmt.setInt(5, employeeDTO.bankId);
            stmt.setDouble(6, employeeDTO.salary);
            stmt.setDouble(7, employeeDTO.salaryBonus);
            stmt.setDate(8, java.sql.Date.valueOf(employeeDTO.startDate));
            stmt.setString(9, employeeDTO.terms);
            stmt.setBoolean(10, employeeDTO.isActive);
            stmt.setBoolean(11, employeeDTO.isLoggedIn);
            stmt.setString(12, employeeDTO.personalState);

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
    public boolean delete(String id) {
        String query = "DELETE FROM employees WHERE id=?";
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            return false;
        }
    }

}