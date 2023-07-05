package DataLayer.DTOs;

import BusinessLayer.IDriver;
import DataLayer.DataMapper;

import java.time.LocalDate;

public class DriverDTO extends DTO {
    public String id;
    public String licenceType;
    public String licenceNumber;
    public boolean tempControlledLicence;

    public DriverDTO(DataMapper controller, String id, String licenceType, String licenceNumber, boolean tempControlledLicence) {
        super(controller);
        this.id = id;
        this.licenceType = licenceType;
        this.licenceNumber = licenceNumber;
        this.tempControlledLicence = tempControlledLicence;
    }
}
