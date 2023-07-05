package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class IdAndPasswordsDTO extends DTO{
    public String id;
    public String password;
    public IdAndPasswordsDTO(DataMapper controller, String id, String password) {
        super(controller);
        this.id=id;
        this.password=password;
    }




}
