package DataLayer;

import DataLayer.DTOs.siteOrderDTO;

import java.sql.*;
import java.util.HashMap;

public class siteOrderMapper extends DataMapper{
    public siteOrderMapper() {
        super("siteOrder");
    }

    @Override
    protected siteOrderDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new siteOrderDTO(this,rs.getString(0),rs.getString(1),rs.getInt(2));
    }
    //get
    public HashMap<String, Integer> getOrderOfShipment(String shipmentId) {
        HashMap<String, Integer> results = new HashMap();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT siteId,sorder FROM siteOrder WHERE shipmentId = ?")) {
            stmt.setString(1, shipmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.put(rs.getString(1),rs.getInt(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return results;
    }
    //insert
    public boolean insert(siteOrderDTO siteOrderDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO siteOrder (shipmentId, siteId, sorder) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, siteOrderDTO.shipmentId);
            stmt.setString(2, siteOrderDTO.siteId);
            stmt.setInt(3, siteOrderDTO.order);

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
    //update
    public boolean updateOrder(String shipmentId,String siteId,int order) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update siteOrder"  + " set " + "order" + "=? where shipmentId=? and siteId=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,order);
                statement.setString(2, shipmentId);
                statement.setString(3, siteId);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    //delete
    public boolean removeSite(String shipmentId,String siteId) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the delete
            String sql = "DELETE FROM siteOrder WHERE shipmentId = ? AND siteId=?";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipmentId);
            stmt.setString(2, siteId);

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

    public boolean insert(String shipmentId, String siteId, int order) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO siteOrder (shipmentId, siteId, sorder) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipmentId);
            stmt.setString(2, siteId);
            stmt.setInt(3, order);

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

    public boolean setOrder(String shipmentId, int from, int to) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update siteOrder"  + " set " + "order=? where shipmentId=? and sorder?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,to);
                statement.setString(2, shipmentId);
                statement.setInt(3, from);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean switchOrder(String shipmentId, int from, int to) {
        return setOrder(shipmentId,from,to) & setOrder(shipmentId,to,from);
    }

    public boolean updateSite(String shipmentId,String siteId,int order) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update siteOrder"  + " set " + "siteId" + "=? where shipmentId=? and sorder=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1,siteId);
                statement.setString(2, shipmentId);
                statement.setInt(3, order);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
}
