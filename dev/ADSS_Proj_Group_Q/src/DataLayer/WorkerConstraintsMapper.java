package DataLayer;

import DataLayer.DTOs.WorkerConstraintsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerConstraintsMapper extends DataMapper {

    public WorkerConstraintsMapper() {
        super("WorkerConstraints");
    }

    @Override
    protected WorkerConstraintsDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date = rs.getDate(2);
        LocalDate localdate = date.toLocalDate();
        return new WorkerConstraintsDTO(this, rs.getString(1), localdate, rs.getString(3));
    }

    public List<WorkerConstraintsDTO> getAllEmployeesConstraints() {
        return select().stream()
                .map(WorkerConstraintsDTO.class::cast)
                .collect(Collectors.toList());
    }

    //insert


    public boolean insertWorkerConstraints(String id, LocalDate date, String time) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO WorkerConstraints (empId , date , shift_Type) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, id);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setString(3, time);

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

    public boolean deleteConstraints(String id, LocalDate date, String time) {
        boolean success = false;
        try {
            Connection connection = DriverManager.getConnection(_connectionstring);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM WorkerConstraints WHERE  empId = ? and date =? and shift_Type =? ");
            statement.setString(1,id);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().toString());
        }
        return success;
    }

    public boolean deleteAllConstraints(LocalDate date, String time) {
        boolean success = false;
        try {
            Connection connection = DriverManager.getConnection(_connectionstring);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM WorkerConstraints WHERE  date =? and shift_Type =? ");
            statement.setDate(1, java.sql.Date.valueOf(date));
            statement.setString(2, time);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().toString());
        }
        return success;
    }
}