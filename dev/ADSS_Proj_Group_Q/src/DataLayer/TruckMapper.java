package DataLayer;


import DataLayer.DTOs.TruckDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TruckMapper extends DataMapper  {
        public TruckMapper() {
            super("trucks");
        }


    protected TruckDTO convertResultSetToObject(ResultSet rs) throws SQLException {

        return new TruckDTO(this,rs.getString(1),rs.getString(2)
                ,rs.getDouble(3),rs.getDouble(4),
                rs.getBoolean(5),rs.getString(6));

    }
    //select
    public List<TruckDTO> getAllTrucks(){
        return select().stream()
                .map(TruckDTO.class::cast)
                .collect(Collectors.toList());
    }
    public boolean updateTruck(String id, String fieldName,String value){

            return update(id,fieldName,value);
    }
    public boolean updateTruck(String id, String fieldName,boolean value){

            return update(id,fieldName,value);
    }
    public boolean updateTruck(String id, String fieldName,double value){

            return update(id,fieldName,value);
    }

    //insert
    public boolean insert(String id,String licenceNumber, String model, double truckWeight, double maxCarryWeight, boolean tempControlled){
        return insertTruck(new TruckDTO(this,licenceNumber,model,truckWeight,maxCarryWeight,tempControlled,id));
    }
    public boolean insertTruck(TruckDTO truckDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO Trucks (licence_number,model,truck_weight,max_carry_weight,temp_controlled,id) " +
                    "VALUES (?, ?, ?, ?, ?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(6, truckDTO.id);
            stmt.setString(1, truckDTO.licenceNumber);
            stmt.setString(2, truckDTO.model);
            stmt.setDouble(3, truckDTO.weight);
            stmt.setDouble(4, truckDTO.maxCarryWeight);
            stmt.setBoolean(5, truckDTO.tempControlled);


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
