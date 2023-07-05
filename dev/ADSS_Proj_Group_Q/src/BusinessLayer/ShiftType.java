package BusinessLayer;

    public enum ShiftType {
        Morning,
        Evening;

        @Override
        public String toString(){
            if(this.name().equals("Morning"))
                return "M";
            else
                return "E";
        }

    }



