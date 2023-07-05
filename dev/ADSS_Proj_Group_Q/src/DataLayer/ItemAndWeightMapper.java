package DataLayer;


import DataLayer.DTOs.ItemAndWeightDTO;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class ItemAndWeightMapper extends DataMapper {

    public ItemAndWeightMapper(){
        super("ItemAndWeight");
    }


    protected ItemAndWeightDTO convertResultSetToObject(ResultSet rs) throws SQLException {

        return new ItemAndWeightDTO(this,rs.getString(1),rs.getDouble(2),rs.getString(3));
    }

    //select
    public List<ItemAndWeightDTO> getAllItemsAndWeight(){
        return select().stream()
                .map(ItemAndWeightDTO.class::cast)
                .collect(Collectors.toList());
    }


    //update
    public boolean updateItemsWeight(String id, String fieldName,Double value){
        return update(id,fieldName,value);
    }



    //insert
    public boolean insert(String item,double weight,String shipId){
        return insertItemAndWeight(new ItemAndWeightDTO(this,item,weight,shipId));
    }
    public boolean insertItemAndWeight(ItemAndWeightDTO itemAndWeightDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO ItemAndWeight (item,weight, ShipmentId) " +
                    "VALUES (?, ?,?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemAndWeightDTO.item);
            stmt.setDouble(2, itemAndWeightDTO.weight);
            stmt.setString(3,itemAndWeightDTO.shipmentId);


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
    // delete
    public boolean deleteShipment(String id){
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM ItemAndWeight WHERE ShipmentId = ?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);

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
    public boolean deleteShipmentItem(String shipId,String itemId){
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM ItemAndWeight WHERE ShipmentId = ? AND item=?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipId);
            stmt.setString(2,itemId);

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

    private boolean removeItemAndWeight(ItemAndWeightDTO itemAndWeightDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM ItemAndWeight WHERE item = ?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemAndWeightDTO.item);

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

    public void delete(int sId, String toRemove) {


    }
}
