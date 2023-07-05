package BusinessLayer;

import DataLayer.DTOs.TruckDTO;
import DataLayer.TruckMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TruckManager {

    ArrayList<Truck> trucks;
    private static int currId = 1;
    private static TruckMapper tMapper= new TruckMapper();
    private static TruckManager instance;

    private TruckManager (){
        trucks = new ArrayList<>();
        instance=this;
    }
    public static TruckManager getInstance(){
        if(instance==null)
            instance=new TruckManager();
        return instance;
    }
    public boolean addTruck(String licenceNum, String model, double weight, double carryWeight,boolean tempControl){
        if(!isLicenceNumberValidForUse(licenceNum)){
            throw new IllegalArgumentException("Invalid Licence number");
        }
        if(!isValidTruckWeight(weight)){
            throw new IllegalArgumentException("weight must be > 0");
        }
        if(!isValidCarryWeight(weight)){
            throw new IllegalArgumentException("weight must be > 0");
        }
        boolean b = false;
        Truck t = new Truck(currId,licenceNum, model,weight,carryWeight,tempControl);
        if(tMapper.insert(String.valueOf(currId),licenceNum, model, weight, carryWeight, tempControl)){
            b = trucks.add(t);
            currId++;
            return b;
        }
        return false;
    }
    public boolean addTruck(Truck t){
        if(!isLicenceNumberValidForUse(t.licenceNumber)){
            throw new IllegalArgumentException("Invalid Licence number");
        }
        if(!isValidTruckWeight(t.weight)){
            throw new IllegalArgumentException("weight must be > 0");
        }
        if(!isValidCarryWeight(t.maxCarryWeight)){
            throw new IllegalArgumentException("weight must be > 0");
        }
        if(tMapper.insert(String.valueOf(t.getId()),t.getLicenceNum(),t.getModel(), t.getWeight(), t.getMaxCarryWeight(), t.isTempControlled())){
            currId = t.getId()<=currId ? t.getId()+1 : currId;
            return trucks.add(t);
        }
        trucks.remove(t);
        throw new RuntimeException("Error while creating new Truck");
    }
    public Truck getTruckByLicenceNumber(String licenceNum){
        for (Truck t: trucks){
            if (t.licenceNumber.equals(licenceNum))
                return t;
        }
        throw new IllegalArgumentException("No truck with licenceNumber of "+ licenceNum + " was found");
    }
    public Truck getTruckByID(int id){
        for (Truck t: trucks){
            if (t.getId() == id){
                return t;
            }
        }
        throw new IllegalArgumentException("no truck with given id exists!");
    }
    public boolean removeTruckById(int ID){
        Truck t = getTruckByID(ID);
        t.setChangedToDelete();
        trucks.remove(getTruckByID(ID));
        t.notifyObservers();
        return true;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0;  i<trucks.size(); i++){
            Truck currTruck = trucks.get(i);
            sb.append(String.format("%d. %s  \t %s \t %1.3f \t %1.3f \t %s\n",
                    currTruck.getId(),currTruck.getLicenceNum(),currTruck.getModel(), currTruck.getWeight(),
                    currTruck.getMaxCarryWeight(),currTruck.isTempControlled()));
        }
        return sb.toString();
    }
    public void changeLicenceNumber(int id, String newLicenceNumber){
        isLicenceNumberValidForUse(newLicenceNumber);
        String oLNum = getTruckByID(id).licenceNumber;
        changeLicenceNumber(getTruckByID(id), newLicenceNumber);
        getTruckByID(id).notifyObservers(oLNum);
        tMapper.updateTruck(String.valueOf(id), "licence_number",newLicenceNumber);
    }
    public void changeModel(int id, String model) {
        changeModel(getTruckByID(id), model);
    }
    public void changeWeight(int id, double weight) throws Exception {
        ensureValidTruckWeight(weight);
        changeTruckWeight(getTruckByID(id), weight);
        getTruckByID(id).notifyObservers(getTruckByID(id).licenceNumber);

    }
    public void changeCarryWeightWeight(int id, double weight) throws Exception {
        ensureValidMaxCarryWeight(weight);
        changeTruckCarryWeight(getTruckByID(id), weight);
    }
    public boolean changeLicenceNumber(Truck toChange, String LicenceNum){
        return toChange.setLicenceNum(LicenceNum);
    }
    public void changeModel(Truck toChange, String model){
        toChange.setModel(model);
        toChange.notifyObservers(toChange.licenceNumber);
        tMapper.updateTruck(String.valueOf(toChange.id), "model", model);
    }
    public boolean changeTruckWeight(Truck toChange, double weight){
        if(!isValidTruckWeight(weight)){
            throw new IllegalArgumentException("Weight must be > 0");
        }
        if(tMapper.updateTruck(String.valueOf(toChange.getId()), "truck_weight", weight)){
            toChange.setWeight(weight);
        }
        return true;
    }
    public boolean changeTruckCarryWeight(Truck toChange, double carryWeight) {
        boolean b  = false;
        if(!isValidCarryWeight(carryWeight)){
            throw new IllegalArgumentException("Weight must be > 0");
        }
        if(tMapper.updateTruck(String.valueOf(toChange.getId()), "max_carry_weight", carryWeight)){
           b  =  toChange.setMaxCarryWeight(carryWeight);
        }
        return b;
    }
    public boolean isLicenceNumberValidForUse(String licenceNumber){
        if (isLicenceNumberAlreadyRegistered((licenceNumber))){
            throw new IllegalArgumentException("Error - truck with the same licence number already registered");
        }
        if (!isValidTruckNum(licenceNumber)){
            throw new IllegalArgumentException("licence number is invalid- please enter a valid licence number");
        }
        return true;
    }
    public boolean isLicenceNumberAlreadyRegistered(String licence){
        for(Truck t: trucks){
            if (t.licenceNumber.equals(licence))
                return true;
        }
        return false;
    }
    public boolean isTruckAlreadyRegistered(Truck toCheck){
        for(Truck t: trucks){
            if (t.equals(toCheck))
                return true;
        }
        return false;
    }

    public int getAmountOfTrucks(){
        return trucks.size();
    }
    public boolean isValidTruckNum(String newLicenceNum){
        Pattern allowed = Pattern.compile("[A-Z0-9]+");
        Matcher matcher = allowed.matcher(newLicenceNum);
        return matcher.matches();
    }
    public void ensureValidTruckWeight(double weight) throws Exception {
        if (!isValidTruckWeight(weight))
            throw new Exception("truck weight must be > 0");
    }
    public void ensureValidMaxCarryWeight(double weight) throws Exception {
        if (!isValidCarryWeight(weight))
            throw new Exception("truck max carry weight must be >= 0");
    }
    public boolean isValidTruckWeight(double weight){
        return weight>0;
    }
    public boolean isValidCarryWeight(double carryWeight){
        return carryWeight>=0;
    }
    public int getTruckIdByLicence(String licenceNumber){
        Truck t = getTruckByLicenceNumber(licenceNumber);
        return trucks.indexOf(t);
    }
    public String truckToString(Truck truck){
        return truck.toString();
    }
    public String truckToString(int index){
        return getTruckByID(index).toString();
    }
    public String truckShortString(int index){
        Truck t = getTruckByID(index);
        String out = String.format("Id: %d\tL.N: %s\tW/MaxW(KG): %3.3f/%3.3f\t Temp.C:%s",index,t.getLicenceNum(),
                t.weight,t.getMaxCarryWeight(),t.tempControlled? "Yes" : "No");
        return out;
    }

    public boolean switchTempControl(int id) {
        tMapper.updateTruck(String.valueOf(id),"temp_controlled",!getTruckByID(id).tempControlled);
        return getTruckByID(id).switchTempControl();
    }
    public void clearForTest(){
        tMapper.deleteAll();
        instance = null;
        currId=1;
        instance=null;
    }

    public void loadTrucks() {
        List<TruckDTO> dtoList = tMapper.getAllTrucks();
        for(TruckDTO t: dtoList){
            Truck truck = new Truck(Integer.valueOf(t.id),t.licenceNumber,t.model,t.weight,t.maxCarryWeight,t.tempControlled);
            if (currId <= Integer.valueOf(t.id)){
                currId = Integer.valueOf(t.id)+1;
            }
            trucks.add(truck);
        }
    }

    public String[][] getTruckTableInfo(String comboBoxInput, boolean checkBox) {
        ArrayList <Truck> arr = checkBox ?  filterByCooling(filterByRequiredLicence(comboBoxInput),true) : filterByRequiredLicence(comboBoxInput);
        String[][] res = new String[arr.size()][4];
        int counter = 0;
        for (Truck t:
             arr) {
            res[counter] = getTruckInfoShort(t.id);
            counter++;
        }
        return res;
    }

    private String[] getTruckInfoShort(int truckId){
        Truck t = getTruckByID(truckId);
        String[] arr = {""+truckId,t.getLicenceNum(),t.getModel(),t.isTempControlled()+""};
        return arr;
    }

    private ArrayList<Truck> filterByRequiredLicence(String licenceTypeString){
        ArrayList <Truck> arrayList = new ArrayList<>();
        switch (licenceTypeString){
            case "None","C1":
                arrayList = trucks;
                break;
            case "B":
                for(Truck t: trucks){
                    if(t.getWeight() <= 3500)
                        arrayList.add(t);
                }
                break;
            case "C":
                for(Truck t: trucks){
                    if(t.getWeight() <= 12000){
                        arrayList.add(t);
                    }
                }
                break;

        }
        return arrayList;
    }

    private static ArrayList<Truck> filterByCooling(ArrayList<Truck> truckList,boolean isCooled){
        ArrayList <Truck> arrayList = new ArrayList<>();
        for (Truck t:
             truckList) {
            if(t.isTempControlled() == isCooled){
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public String[] getTruckModelData(String truckId) {
       Truck t =  getTruckByID(Integer.valueOf(truckId));
       String [] arr = {t.id+"", t.getLicenceNum(),t.getModel(),String.valueOf(t.getWeight()),
               String.valueOf(t.getMaxCarryWeight()) ,t.isTempControlled()+"" };
       return arr;
    }

    public String[][] getTruckArrays() {
        String[][] out = new String[2][getAmountOfTrucks()];
        for (int i = 0; i < trucks.size(); i++) {
            Truck t = trucks.get(i);
            out[0][i] = truckShortString(t.id);
            out[1][i] = ""+t.id;
        }
        return out;
    }

    public String truckShorterString(int index) {
        Truck t = getTruckByID(index);
        String out = String.format("Id: %d L.N: %s : %s",index,t.getLicenceNum(),t.getModel());
        return out;
    }
}
