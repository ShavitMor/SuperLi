package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.ShipmentDTO;
import DataLayer.DTOs.ShipmentDestAndAmountsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipmentDestAndAmountsMapper extends DataMapper{
    public ShipmentDestAndAmountsMapper() {
        super("ShipmentDestsAndAmounts");
    }

    @Override
    protected ShipmentDestAndAmountsDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new ShipmentDestAndAmountsDTO(this,rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4));
    }
    //get
    public List<String> getShipmentDest(String id) {
        List<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT destId FROM ShipmentDestsAndAmounts WHERE shipmentId =?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return results;
    }
    public HashMap<String,Integer> getShipmentDestAmount(String id, String dId) {
        HashMap<String,Integer> results = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT itemId,amount FROM ShipmentDestsAndAmounts WHERE shipmentId =? and destId=?")) {
            stmt.setString(1, id);
            stmt.setString(2, dId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.put(rs.getString(1),rs.getInt(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }
    //insert
    public boolean insertDest(String shId,String dId){
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO ShipmentDestsAndAmounts (shipmentId , destId,itemId,amount) " +
                    "VALUES (?, ?,null,0)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, shId);
            stmt.setString(2, dId);


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
    public boolean insertDestItemAndAmount(String shId,String sId,String iId,int amount){
        return insertDestItemAndAmount(new ShipmentDestAndAmountsDTO(this,shId,sId,iId,amount));
    }
    private boolean insertDestItemAndAmount(ShipmentDestAndAmountsDTO shipmentDestAndAmountsDTO){
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO ShipmentDestsAndAmounts (shipmentId , destId,itemId,amount) " +
                    "VALUES (?, ?,?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, shipmentDestAndAmountsDTO.shipmentId);
            stmt.setString(2, shipmentDestAndAmountsDTO.siteId);
            stmt.setString(3,shipmentDestAndAmountsDTO.itemId);
            stmt.setInt(4,shipmentDestAndAmountsDTO.itemAmount);


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
    public boolean updateShipmentItemAmount(String shipId,String destId,String itemId,int amount){
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update ShipmentDestsAndAmounts set amount=? where shipmentId=? and destId=? and itemId=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, amount);
                statement.setString(2, shipId);
                statement.setString(3, destId);
                statement.setString(4, itemId);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    //delete
    public boolean removeShipment(String id) {
        String query = "DELETE FROM ShipmentDestsAndAmounts WHERE shipmentId=?";
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
    public boolean removeShipmentDest(String shId,String destId) {
        String query = "DELETE FROM ShipmentDestsAndAmounts WHERE shipmentId=? and destId=?";
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shId);
            statement.setString(2, destId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            return false;
        }
    }
    public boolean removeShipmentDestItem(String shId,String destId,String itemId) {
        String query = "DELETE FROM ShipmentDestsAndAmounts WHERE shipmentId=? and destId=? and itemId=?";
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shId);
            statement.setString(2, destId);
            statement.setString(3,itemId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            return false;
        }
    }


}
