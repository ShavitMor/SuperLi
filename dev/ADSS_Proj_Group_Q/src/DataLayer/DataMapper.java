package DataLayer;

import DataLayer.DTOs.DTO;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public abstract class DataMapper {
    protected String _connectionstring;
    private  String _tableName;

    public DataMapper(String tableName) {
        String path =  "src/assignment2.db";
        this._connectionstring = "jdbc:sqlite:" + path;
        this._tableName = tableName;
    }
    public String GetConnectionString() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return this._connectionstring;
    }
    public boolean update(long id, String attributeName, String attributeValue, String connectionString) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + _tableName + " SET [" + attributeName + "]=? WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, attributeValue);
                stmt.setLong(2, id);
                res = stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

//===========================================
    public boolean update(String id, String attributeName, String attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    public boolean update(String id, String attributeName, int attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(String id, String attributeName, Date attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(String id, String attributeName, double attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDouble(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(String id, String attributeName, boolean attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBoolean(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    public boolean update(String id, String attributeName, Time attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + attributeName + "]=? where id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTime(1, attributeValue);
                statement.setString(2, id);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }


    public boolean update(LocalDate date, String shiftType, String branch, String attributeName, String attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? and branch=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(LocalDate date, String shiftType, String branch, String attributeName, int attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? and branch=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(LocalDate date, String shiftType, String branch, String attributeName, boolean attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shift_type=? and branch=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBoolean(1, attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(LocalDate date, String shiftType, String branch, String attributeName, LocalDate attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? and branch=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, Date.valueOf(attributeValue));
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(LocalDate date, String shiftType, String branch, String attributeName, LocalTime attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? and branch=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTime(1, Time.valueOf(attributeValue));
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    public boolean update(LocalDate date, String shiftType, String branch,String role ,String attributeName, int attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? and branch=? and role=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);
                statement.setString(4, branch);
                statement.setString(5, role);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }
    public boolean update(LocalDate date, String shiftType, String attributeName, int attributeValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set " + attributeName + "=? where date=? and shiftType=? ";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,attributeValue);
                statement.setDate(2, java.sql.Date.valueOf(date));
                statement.setString(3, shiftType);

                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
        }
        return res > 0;
    }

    //======================================================================


























    //not us


    public boolean update(long id, String findAttribute, int findAttributeValue, String updateAttribute, int updateValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            String query = "update " + _tableName + " set [" + updateAttribute + "]=? where id=? and [" + findAttribute + "]=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, updateValue);
                statement.setLong(2, id);
                statement.setInt(3, findAttributeValue);
                res = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res > 0;
    }
    public boolean update(long id, String findAtrribute, int findAttributeValue, String updateAttribute, String updateValue) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("update %s set [%s]=? where id=? and %s=?", _tableName, updateAttribute, findAtrribute)
            );
            statement.setString(1, updateValue);
            statement.setLong(2, id);
            statement.setInt(3, findAttributeValue);
            res = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res > 0;
    }

    public boolean delete(DTO DTOObj) {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring)) {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("delete from %s where id=?", _tableName)
            );
           // statement.setLong(1, DTOObj.getId());
            res = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res > 0;
    }

    public int deleteAll() {
        int res = -1;
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             Statement stmt = connection.createStatement()) {
            res = stmt.executeUpdate(String.format("DELETE FROM %s", _tableName));
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res;
    }

    protected List<DTO> select() {
        List<DTO> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionstring);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s", _tableName))) {
            while (rs.next()) {
                results.add(convertResultSetToObject(rs));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return results;
    }
    protected abstract DTO convertResultSetToObject(ResultSet rs) throws SQLException;

}
