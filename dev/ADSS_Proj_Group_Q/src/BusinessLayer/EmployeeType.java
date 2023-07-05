package BusinessLayer;

public enum EmployeeType {
    ShiftManager  ,
    SalesMan,
    StoreKeeper,
    Security,
    Clean,
    Usher,
    General,
    Driver;

    @Override
    public String toString() {
        switch (this.name()){
            case  ("ShiftManager"):
                return "shm";
            case  ("SalesMan"):
                return "sm";
            case ("StoreKeeper"):
                return "sk";
            case ("Security"):
                return "se";
            case ("Clean"):
                return "c";
            case ("Usher"):
                return "u";
            case ("General"):
                return "g";
            case ("Driver"):
                return "d";
        }
        return "";
    }
}


//מנהל משמרת, קופאי,מחסנאי, אבטחה, נקיון, סדרן ועובד כללי.