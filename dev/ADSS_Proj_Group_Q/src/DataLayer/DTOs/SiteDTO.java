package DataLayer.DTOs;

import DataLayer.DataMapper;

public class SiteDTO extends DTO{
    public String id;
    public String name;
    public String address;
    public String contactName;
    public String contactPhone;
    public String type;
    public int x;
    public int y;
    public boolean active;
    public SiteDTO(DataMapper controller,String id,String name,String address,String contactName,String contactPhone,String type,int x,int y,boolean active ) {
        super(controller);
        this.id=id;
        this.name=name;
        this.address=address;
        this.contactName=contactName;
        this.contactPhone=contactPhone;
        this.type=type;
        this.x=x;
        this.y=y;
        this.active=active;
    }

}
