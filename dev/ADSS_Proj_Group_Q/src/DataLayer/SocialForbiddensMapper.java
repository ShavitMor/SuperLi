package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.SocialForbiddensDTO;
import DataLayer.DTOs.TruckDTO;
import DataLayer.DTOs.WorkerConstraintsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class SocialForbiddensMapper extends DataMapper{
    public SocialForbiddensMapper() {
        super("SocialForbiddens");
    }

    @Override
    protected SocialForbiddensDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate(1).toLocalDate();
        return new SocialForbiddensDTO(this,date,rs.getString(2),rs.getInt(3));
    }
    //getter
    public List<SocialForbiddensDTO> getAllSocialForbiddens(){
        return select().stream()
                .map(SocialForbiddensDTO.class::cast)
                .collect(Collectors.toList());
    }
    //insert
    public boolean insertWorkeSocialrForbidden(LocalDate lc,String empId,int amount) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO SocialForbiddens (date ,emp_ID , amount) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(lc));
            stmt.setString(2, empId);
            stmt.setInt(3,amount);

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
    public boolean updateSocialForBid(LocalDate lc,String empId,int amount){
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update SocialForbiddens set amount=? where date=? and emp_ID=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, amount);
                statement.setDate(2, Date.valueOf(lc));
                statement.setString(3, empId);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

}
