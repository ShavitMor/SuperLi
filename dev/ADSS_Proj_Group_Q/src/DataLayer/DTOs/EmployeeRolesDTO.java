package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRolesDTO extends DTO{
    public String id;
    public String role;
    public EmployeeRolesDTO(DataMapper controller, String id, String role) {
        super(controller);
        this.id=id;
        this.role=role;
    }


}
