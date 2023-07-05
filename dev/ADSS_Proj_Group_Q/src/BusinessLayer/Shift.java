package BusinessLayer;

import DataLayer.*;
import DataLayer.DTOs.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shift {
    protected LocalDate date;
    protected LocalTime begin;
    protected int shiftTime;
    protected LocalTime end;
    protected ShiftType shiftType;
    protected Site branch;


    private HashMap<EmployeeType, Integer> rolesNeeded;//tafkid - kamoot
    protected HashMap<EmployeeType, List<Employee>> workers; //tafkid - oved
    protected ArrayList<String> specialEvents;//eruim meyhuadim
    protected HashMap<Integer, Integer> cancelations;
    private String shiftManagerID;
    private boolean isPublished;
    public void setshiftManagerID(String id){
         shiftManagerID=id;
    }
    private RolesNeededMapper rolesNeededMapper;
    private ShiftWorkerMapper shiftWorkerMapper;
    private ShiftEventsMapper shiftEventsMapper;
    private CancelationsMapper cancelationsMapper;
    private ShiftMapper shiftMapper;

    public Shift(LocalDate date, ShiftType shiftType, int mbegin, int shiftTime, Site branch) {
        this.date = date;
        this.shiftTime = shiftTime;
        begin = LocalTime.of(mbegin, 0);
        this.end = LocalTime.of(begin.getHour() + shiftTime, begin.getMinute(), 0);
        this.shiftType = shiftType;
        this.branch = branch;
        rolesNeeded = new HashMap<>();
        workers = new HashMap<>();
        shiftManagerID = null;//will be init when put shift
        isPublished = false;
        rolesNeededMapper = new RolesNeededMapper();
        rolesNeededMapper.insertRolesNeeded(date,shiftType.toString(),branch.getName(),"shm",1);
        rolesNeeded.put(EmployeeType.ShiftManager, 1);
        specialEvents = new ArrayList<>();
        cancelations = new HashMap<>();

        shiftWorkerMapper = new ShiftWorkerMapper();
        shiftEventsMapper = new ShiftEventsMapper();
        cancelationsMapper=new CancelationsMapper();
        shiftMapper=new ShiftMapper();

    }

    public Shift(ShiftDTO shiftDTO) {
        rolesNeededMapper = new RolesNeededMapper();
        shiftWorkerMapper = new ShiftWorkerMapper();
        shiftEventsMapper = new ShiftEventsMapper();
        cancelationsMapper=new CancelationsMapper();
        shiftMapper=new ShiftMapper();
        this.date = shiftDTO.date;
        this.shiftTime = shiftDTO.shiftTime;
        this.begin = shiftDTO.begin;
        this.end = shiftDTO.end;
        this.shiftType = ShiftController.getInstance().getShiftType(shiftDTO.shiftType);
        this.branch = SiteManager.getInstance().getFirstMatchingBranchByName(shiftDTO.branch);
        this.rolesNeeded = getRolesNeeded(rolesNeededMapper.getRolesNeeded(date, shiftDTO.shiftType, shiftDTO.branch));
        this.workers = initWorkers(shiftDTO);
        this.specialEvents=shiftEventsMapper.getShiftEvents(shiftDTO.date,shiftDTO.shiftType,shiftDTO.branch);
        this.cancelations=cancelationsMapper.getCancelations(shiftDTO.date,shiftDTO.shiftType,shiftDTO.branch);
        this.isPublished=shiftDTO.isPublished;


    }


    public Site getBranch() {
        return branch;
    }


    protected void setStart(int hour, int minutes) {
        begin = LocalTime.of(hour, minutes);
    }


    protected int getShiftTime() {

        return shiftTime;
    }

    protected LocalDate getDate() {
        return date;
    }

    public void setRoleToShift(EmployeeType role, int quantity) {
        //add role to the shift
        if (role == EmployeeType.ShiftManager&&quantity!=0)
            throw new IllegalArgumentException("you can't add another manager in the shift");
        if(rolesNeeded.containsKey(role)){

            if(rolesNeededMapper.update(date,shiftType.toString(),branch.name,role.toString(),"amount",quantity)){
            rolesNeeded.put(role, quantity);
            }

        }else {
            if(rolesNeededMapper.insertRolesNeeded(date, shiftType.toString(), branch.name, role.toString(), quantity))
                rolesNeeded.put(role, quantity);
        }
        }


    public void addWorkerToShift(Employee employee, EmployeeType employeeType) {
        if(shiftWorkerMapper.insertWorkerToShift(date,shiftType.toString(),branch.name,employee.id,employeeType.toString())) {
            List<Employee> lst;
            if (workers.containsKey(employeeType))
                lst = workers.get(employeeType);
            else
                lst = new ArrayList<>();
            lst.add(employee);
            workers.put(employeeType, lst);
            //rolesNeeded.put(employeeType, rolesNeeded.get(employeeType) - 1);
            if (employeeType == EmployeeType.ShiftManager)
                shiftManagerID = employee.getId();
        }

    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public HashMap<EmployeeType, Integer> getRolesNeeded() {
        return rolesNeeded;
    }

    public HashMap<EmployeeType, List<Employee>> getWorkersinShift() {
        return workers;
    }
    protected boolean isWork(Employee employee){
        for(EmployeeType employeeType:workers.keySet())
            for(Employee employee1:workers.get(employeeType))
                if(employee1.equals(employee))
                    return true;
                return false;
    }

    protected void setPublished() {
        if (isPublished)
            throw new IllegalArgumentException("shift already published");
        if (shiftManagerID == null)
            throw new IllegalArgumentException("there is no mannager for the shift");
        int sumNeeded = 0;
        for (EmployeeType employeeType : rolesNeeded.keySet()) {
            sumNeeded += rolesNeeded.get(employeeType);
        }
        int sumWorkers = workers.size();
        if (sumNeeded > sumWorkers)
            System.out.println("notice! the shift isn't full");
        if(shiftMapper.updateShift(date,shiftType.toString(),branch.name,"is_published",true)){
            isPublished = true;
        }

    }

    public String toString() {
        //override to string
        String ret = "shift at: " + date + " " + shiftType.name() + " at branch: " + branch.name + " at hours: " + begin + "-" + end + " workers: ";
        for (List<Employee> lst : workers.values()) {
            for (Employee emp : lst) {
                ret = ret + "\n" + emp.getId() + " " + emp.getName();
            }
        }
        return ret;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public String getShiftWorker(Employee employee) {
        //return shift time if worker works at it
        for (EmployeeType employeeType : workers.keySet()) {
            if (workers.get(employeeType).contains(employee))
                return "date: "+date.getDayOfMonth()+"/"+date.getMonth().getValue()+"/"+date.getYear() + " on: " + begin.toString() + "-" + end.toString();
        }
        return null;
    }


    public String getShiftManagerID() {
        return shiftManagerID;
    }


    public boolean equals(Shift other) {
        return other.date.equals(date) && other.shiftType == shiftType && other.branch == branch;
    }

    public ArrayList<String> getSpecialEvents() {
        return specialEvents;
    }

    public List<String> getCancelation() {
        List<String> ret = new ArrayList<>();
        String s = "Branch: " + branch.name+ "Date:" + date + " Type:" + shiftType.toString() + " manager: " + shiftManagerID;
        ret.add(s);
        for (Integer prod : cancelations.keySet()) {
            s = "product id: " + prod + " quantity: " + cancelations.get(prod);
            ret.add(s);
        }
        return ret;
    }

    protected boolean isHaveStoreKeeper() {

        return workers.containsKey(EmployeeType.StoreKeeper);

    }

    protected void addShiftEvent(String event) {
        if(shiftEventsMapper.insertShiftEvent(date,shiftType.toString(),branch.name,event)){
        specialEvents.add(event);}
    }

    protected void addShiftCancelation(Integer prodId, Integer quantity) {
        if(cancelationsMapper.insert(date,shiftType.toString(),branch.name,prodId,quantity)){
            cancelations.put(prodId, quantity);
        }

    }


    public HashMap<EmployeeType, Integer> getRolesNeeded(HashMap<String, Integer> map) {

        HashMap<EmployeeType, Integer> ans = new HashMap<>();

        for (String s : map.keySet()) {
            ans.put(EmployeeController.getInstance().gEmployeeType(s), map.get(s));
        }
        return ans;
    }

    public HashMap<EmployeeType, List<Employee>> initWorkers(ShiftDTO shiftDTO) {
        HashMap<EmployeeType, List<Employee>> ans = new HashMap<>();
        EmployeeType[] arr = {EmployeeType.ShiftManager,
                EmployeeType.SalesMan, EmployeeType.StoreKeeper,
                EmployeeType.Security, EmployeeType.Clean, EmployeeType.Usher,
                EmployeeType.General, EmployeeType.Driver};

        for (int i = 0; i < arr.length; i++) {
            ans.put(arr[i], shiftWorkerMapper.getWorkersByTypeAndShift(shiftDTO.date, shiftDTO.shiftType, shiftDTO.branch, arr[i].toString()));
        }
        if(ans.get(EmployeeType.ShiftManager)!=null&&!ans.get(EmployeeType.ShiftManager).isEmpty())
            shiftManagerID=ans.get(EmployeeType.ShiftManager).get(0).getId();
        return ans;
    }


    protected boolean isStoreKeeperOrShiftManager(Employee emp){
        if(shiftManagerID==emp.id)
            return true;

        if(workers.containsKey(EmployeeType.StoreKeeper)){
            if(workers.get(EmployeeType.StoreKeeper).contains(emp)){
                return true;
            }
        }
        return false;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public HashMap<EmployeeType, List<Employee>> getWorkers() {
        return workers;
    }

    public HashMap<Integer, Integer> getCancelations() {
        return cancelations;
    }

    public boolean isPublished() {
        return isPublished;
    }
}


