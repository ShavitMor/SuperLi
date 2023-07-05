package DataLayer.DTOs;

import DataLayer.DataMapper;

public abstract class DTO
{
    protected DataMapper _controller;


    public DTO(DataMapper controller) { _controller = controller; }

}
