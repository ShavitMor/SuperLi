package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.EmployeeDTO;
import DataLayer.DTOs.SiteDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SiteMapper extends DataMapper{
    public SiteMapper() {
        super("sites");
    }

    @Override
    protected SiteDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new SiteDTO(this,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getBoolean(9));
    }
    //select
    public List<SiteDTO> getAllSites(){
        return select().stream()
                .map(SiteDTO.class::cast)
                .collect(Collectors.toList());
    }
    //insert
    public boolean insertSite(String id,String name,String address,String contactName,String contactPhone,String type,int x,int y,boolean active) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO sites (id, name, address,contactName,contactPhone,type,x,y,active) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setString(4, contactName);
            stmt.setString(5, contactPhone);
            stmt.setString(6, type);
            stmt.setInt(7,x);
            stmt.setInt(8, y);
            stmt.setBoolean(9, active);


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
    //update
    public boolean updateSite(String id, String attributeName, String attributeValue){
        return update(id,attributeName,attributeValue);
    }
    public boolean updateSite(String id, String attributeName, boolean attributeValue){
        return update(id,attributeName,attributeValue);
    }

    public boolean updateSite(String sId, int x, int y) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "UPDATE sites SET x=?, y=? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, x);
                statement.setInt(2, y);
                statement.setString(3, sId);

                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
}
