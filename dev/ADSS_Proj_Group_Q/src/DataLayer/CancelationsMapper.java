package DataLayer;

import DataLayer.DTOs.CancelationsDTO;
import DataLayer.DTOs.DTO;
import DataLayer.DTOs.EmployeeDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CancelationsMapper extends DataMapper{



    public CancelationsMapper() {
            super("Cancelations");
        }


    //select
    public List<CancelationsDTO> getAllCancelations(){
        return select().stream()
                .map(CancelationsDTO.class::cast)
                .collect(Collectors.toList());
    }


    @Override
    protected CancelationsDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate("date").toLocalDate();
        String shiftType = rs.getString("shiftType");
        String branch = rs.getString("branch");
        int productID = rs.getInt("productID");
        int amount = rs.getInt("amount");

        return new CancelationsDTO(this, date, shiftType, branch, productID, amount);
    }

    public HashMap<Integer,Integer> getCancelations(LocalDate date, String time, String branch) {
        HashMap<Integer,Integer>  results = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT productID,amount FROM Cancelations WHERE date = ? AND shiftType = ? AND branch = ?")) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setString(3, branch);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return results;
    }

        public boolean insert(LocalDate date,String time,String branch,int productID,int amount) {
            try {
                // Create a new connection to the database
                Connection conn = DriverManager.getConnection(_connectionstring);

                // Construct the SQL statement for the insert
                String sql = "INSERT INTO cancelations (date, shiftType, branch, productID, amount) " +
                        "VALUES (?, ?, ?, ?, ?)";

                // Prepare the statement with the parameters
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setDate(1, java.sql.Date.valueOf(date));
                stmt.setString(2, time);
                stmt.setString(3, branch);
                stmt.setInt(4, productID);
                stmt.setInt(5, amount);

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

        public boolean delete(CancelationsDTO cancelationsDTO) {
            try {
                // Create a new connection to the database
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

                // Construct the SQL statement for the delete
                String sql = "DELETE FROM cancelations WHERE date = ? AND shiftType = ? AND branch = ? AND productID = ? AND amount = ?";

                // Prepare the statement with the parameters
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setDate(1, java.sql.Date.valueOf(cancelationsDTO.date));
                stmt.setString(2, cancelationsDTO.shiftType);
                stmt.setString(3, cancelationsDTO.branch);
                stmt.setInt(4, cancelationsDTO.productID);
                stmt.setInt(5, cancelationsDTO.amount);

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
    }


