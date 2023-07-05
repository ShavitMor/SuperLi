package BusinessLayer;


public interface IDriver {
    boolean setLicenceType(String newLicenceType);
    LicenceType getLicenceType();
    boolean setLicenceNumber(String newLicenceNumber);
    String getLicenceNumber();
    int getMaxDriveWeight();
    boolean isCanDriveTempControlled();

    public enum LicenceType{
        B,
        C1,
        C;

        /**
         *
         * @return max allowed weight to drive in israel, values are in KG
         */

        public int getWeightLimit() {
            int noLimit = Integer.MAX_VALUE;
            switch (this){
                case B:
                    return 3500;
                case C1:
                    return 12000;
                case C:
                    return noLimit; //No limit by law
            }
            return 0;
        }

        public static LicenceType getRequiredLicenceByWeight(double weight){
            if(LicenceType.B.getWeightLimit() >= weight)
                return B;
            if(LicenceType.C1.getWeightLimit() >= weight)
                return C1;
            if(LicenceType.C.getWeightLimit() >= weight)
                return C;
            return null;
        }

    }

}
