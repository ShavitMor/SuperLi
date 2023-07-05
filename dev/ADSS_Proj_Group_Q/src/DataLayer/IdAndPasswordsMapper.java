package DataLayer;

import DataLayer.DTOs.IdAndPasswordsDTO;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class IdAndPasswordsMapper extends DataMapper{

    public IdAndPasswordsMapper() {
        super("IdAndPasswords");
    }

    @Override
    protected IdAndPasswordsDTO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new IdAndPasswordsDTO(this,rs.getString(1),rs.getString(2));
    }
    public List<IdAndPasswordsDTO > getIdsAndPasswords(){
        return select().stream()
                .map(IdAndPasswordsDTO.class::cast)
                .collect(Collectors.toList());
    }
    //insert
    public boolean insertIdAndPassword( String id, String password) {
        return insertIdAndPassword(new IdAndPasswordsDTO(this, id, password));
    }

    public boolean insertIdAndPassword(IdAndPasswordsDTO idAndPasswordsDTO) {
        try {
            // Create a new connection to the database
            Connection conn = DriverManager.getConnection(_connectionstring);

            // Construct the SQL statement for the insert
            String sql = "INSERT INTO IdAndPasswords (id , password) " +
                    "VALUES (?, ?)";

            // Prepare the statement with the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, idAndPasswordsDTO.id);
            stmt.setString(2, idAndPasswordsDTO.password);


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
    public boolean delete(String id) {
        String query = "DELETE FROM IdAndPasswords WHERE id=?";
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