package BusinessLayer;

import java.time.LocalDate;


public record dateAndType(LocalDate date , ShiftType type ) {


public boolean equals(dateAndType other){
    boolean ans=false;
    if(date.equals(other.date) && type.equals(other.type)){
        ans=true;
    }
    return ans;


}

}





