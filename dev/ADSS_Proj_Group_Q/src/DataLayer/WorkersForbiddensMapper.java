package DataLayer;

import DataLayer.DTOs.DTO;
import DataLayer.DTOs.DriversShiftsDTO;
import DataLayer.DTOs.WorkersForbiddensDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class WorkersForbiddensMapper extends DataMapper {
    public WorkersForbiddensMapper() {
        super("WorkersForbiddens");
    }

    @Override
    protected WorkersForbiddensDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        Date date = rs.getDate(1);
        LocalDate localdate = date.toLocalDate();
        return new WorkersForbiddensDTO(this,localdate,rs.getString(2),rs.getString(3));
    }
    //getter
    public List<WorkersForbiddensDTO> getMannagerForbidens() {
        java.util.List<WorkersForbiddensDTO> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             PreparedStatement stmt = connection.prepareStatement("SELECT date,shift_Type,empId FROM WorkersForbiddens")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                java.sql.Date date1 = rs.getDate(1);
                LocalDate localdate = date1.toLocalDate();
                results.add(new WorkersForbiddensDTO(this,localdate,rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return results;
    }


    //insert
    public boolean insertMannagerForbidens(LocalDate lc,String time,String id){
        return insertWorkerForbidden(new WorkersForbiddensDTO(this,lc,time,id));
    }

    private boolean insertWorkerForbidden(WorkersForbiddensDTO workersForbiddensDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO WorkersForbiddens(date , shift_Type,empId) " +
                    "VALUES (?, ?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, java.sql.Date.valueOf(workersForbiddensDTO.date));
            stmt.setString(2, workersForbiddensDTO.time);
            stmt.setString(3, workersForbiddensDTO.id);

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
    public boolean deleteWorkerForbiden(DataMapper controller, LocalDate date, String time, String id) {
        return delete(new WorkersForbiddensDTO(this,  date, time,id));
    }
    private boolean delete(WorkersForbiddensDTO workersForbiddensDTO) {
        boolean success = false;
        try {
            Connection connection = DriverManager.getConnection(_connectionstring);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM WorkersForbiddens WHERE  empId = ? and date =? and shift_Type =? ");
            statement.setString(1, workersForbiddensDTO.id);
            statement.setDate(2, java.sql.Date.valueOf(workersForbiddensDTO.date));
            statement.setString(3, workersForbiddensDTO.time);

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
