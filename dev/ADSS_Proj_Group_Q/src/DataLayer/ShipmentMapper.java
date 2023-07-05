package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.EmployeeDTO;
import DataLayer.DTOs.IdAndPasswordsDTO;
import DataLayer.DTOs.ShipmentDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentMapper extends DataMapper{
    public ShipmentMapper() {
        super("Shipments");
    }

    @Override
    protected ShipmentDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date=rs.getDate(2);
        LocalDate localdate = date == null ? null : date.toLocalDate();
        Time time=rs.getTime(3);
        LocalTime localTime=time==null ? null : time.toLocalTime();
        return new ShipmentDTO(this,rs.getString(1),localdate,localTime,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
    }
    //getter
    public List<ShipmentDTO> getShipments(){
        return select().stream()
                .map(ShipmentDTO .class::cast)
                .collect(Collectors.toList());
    }
    public List<ShipmentDTO> getShipmentByStatus(String id){
        List<ShipmentDTO> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT role FROM Shipments WHERE status =?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(convertResultSetToObject(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }
    //update
    public boolean updateShipment(String id, String fieldName,String value){
        return update(id,fieldName,value);
    }
    public boolean updateShipment(String id, String fieldName,int value){
        return update(id,fieldName,value);
    }
    public boolean updateShipment(String id, String fieldName, java.sql.Date value){
        return update(id,fieldName,value);
    }
    public boolean updateShipment(String id, String fieldName, Time value){
        return update(id,fieldName,value);
    }
    public boolean insert(String id,LocalDate lc,LocalTime lt,String tr,String dr,String mzone,String st){
        return insertShipment(new ShipmentDTO(this,id,lc,lt,tr,dr,mzone,st));
    }
    //insert
    public boolean insertShipment(ShipmentDTO shipmentDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO Shipments (id, date ,StartTime ,truckId, driverId,  zone , status,totalDeliveryWeight) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipmentDTO.id);
            if(shipmentDTO.shippingDate!=null)
                stmt.setDate(2, java.sql.Date.valueOf( shipmentDTO.shippingDate));
            else
                stmt.setDate(2, null);
            if(shipmentDTO.shippingStartTime!=null)
                stmt.setTime(3,Time.valueOf( shipmentDTO.shippingStartTime));
            else
                stmt.setTime(3,null);
            stmt.setString(4, shipmentDTO.truck);
            stmt.setString(5, shipmentDTO.driver);
            stmt.setString(6, shipmentDTO.zone);
            stmt.setString(7, shipmentDTO.status);
            stmt.setDouble(8, shipmentDTO.totalDeliveryWeight);

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
    public boolean removeShipment(String id) {
        String query = "DELETE FROM Shipments WHERE id=?";
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
