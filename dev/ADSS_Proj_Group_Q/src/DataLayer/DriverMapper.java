package DataLayer;

import BusinessLayer.Driver;
import DataLayer.DTOs.DriverDTO;
import DataLayer.DTOs.EmployeeDTO;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMapper extends DataMapper  {
    public DriverMapper() {
        super("drivers");
    }

    @Override
    protected DriverDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new DriverDTO(this,rs.getString(1),rs.getString(2),rs.getString(3),
                rs.getBoolean(4));
    }

    //select
    public List<DriverDTO> getAllDrivers(){
        return select().stream()
                .map(DriverDTO.class::cast)
                .collect(Collectors.toList());
    }


    //update
    public boolean updateDriver(String id, String fieldName,String value){
        return update(id,fieldName,value);
    }

    public boolean updateDriver(String id, String fieldName,boolean value){
        return update(id,fieldName,value);
    }



    //insert
    public boolean insert(String id, String licenceType, String licenceNumber, boolean tempControlledLicence){
        return insertDriver(new DriverDTO(this,id,licenceType,licenceNumber,tempControlledLicence));
    }
    public boolean insertDriver(DriverDTO driverDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO drivers (id,licence_type,licence_number,temp_controlled_licence) " +
                    "VALUES (?,?,?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, driverDTO.id);
            stmt.setString(2, driverDTO.licenceType);
            stmt.setString(3, driverDTO.licenceNumber);
            stmt.setBoolean(4, driverDTO.tempControlledLicence);


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