package BusinessLayer;

import DataLayer.*;
import DataLayer.DTOs.DriverDTO;
import DataLayer.DTOs.EmployeeDTO;
import DataLayer.DTOs.IdAndPasswordsDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class EmployeeController {
    
//------------------------fields-------------------------//
    private HashMap<String,Employee> workers;
    private static EmployeeController instance;
    private static List<String> optionsOfRole;
    private static HashMap<String,String> idAndPasswords;
    private static String humanResurceId;
    private static String shipmentManagerId;


    private EmployeeMapper employeeMapper;
    private IdAndPasswordsMapper idAndPasswordsMapper;
    private DriverMapper driverMapper;
    private EmployeeRolesMapper employeeRolesMapper;


    private EmployeeController(){
        workers=new HashMap<>();
        instance=this;
        optionsOfRole=new ArrayList<>();
        idAndPasswords =new HashMap<>();
        humanResurceId=null;
        shipmentManagerId=null;
        optionsOfRole.add("shm");
        optionsOfRole.add("sm");
        optionsOfRole.add("c");
        optionsOfRole.add("g");
        optionsOfRole.add("sk");
        optionsOfRole.add("u");
        optionsOfRole.add("se");
        optionsOfRole.add("d");
        employeeMapper=new EmployeeMapper();
        driverMapper=new DriverMapper();
        idAndPasswordsMapper=new IdAndPasswordsMapper();
        employeeRolesMapper=new EmployeeRolesMapper();
    }

    public String[] getEmployeeDetails(String id) {
        Employee emp=getEmployee(id);
        String[] ans={emp.id,emp.name,emp.bankCompany,emp.bankBranch+"",emp.bankId+"",emp.salary+""
        ,emp.salaryBonus+"",emp.startDate.toString(),emp.terms,emp.roles.stream()
                .map(role -> role.name())
                .collect(Collectors.joining(", ")),emp.isActive+"",emp.personalState,emp.isLoggedIn+""};

    return ans;
    }

    public String[] getDriverDetails(String id){
        Driver drive=getDriver(id);
        String[] ans={String.valueOf(drive.licenceType),drive.licenceNumber, String.valueOf(drive.tempControlledLicence)};
        return ans;

    }
//        public String id;
//        protected String name;
//        public String bankCompany;
//        public int bankBranch;
//        public int bankId;
//        public double salary; //per hour
//        public double salaryBonus;
//        public LocalDate startDate;
//        public String terms; //tnaey aasaka
//        public List<EmployeeType> roles;
//        protected boolean isActive;
//        public String personalState;
//        protected boolean isLoggedIn;

    public void LoadEmployees(){
        List<String> hr=employeeRolesMapper.gethumanResurceId();
        if(!hr.isEmpty())
            humanResurceId=hr.get(0);
        hr=employeeRolesMapper.getshipmentManagerId();
        if(!hr.isEmpty())
            shipmentManagerId=hr.get(0);
        List<EmployeeDTO> employeeDTOS=employeeMapper.getAllEmployees();
        for(EmployeeDTO employeeDTO:employeeDTOS){
            Employee employee=new Employee(employeeDTO);
            workers.put(employee.getId(),employee);
        }
        List<DriverDTO> driverDTOS=driverMapper.getAllDrivers();
        for(DriverDTO driverDTO:driverDTOS){
            Employee driver=new Driver(workers.get(driverDTO.id),driverDTO.licenceNumber,driverDTO.licenceType,driverDTO.tempControlledLicence);
            workers.put(driver.getId(),driver);
        }
        List<IdAndPasswordsDTO> idAndPasswordsDTOS=idAndPasswordsMapper.getIdsAndPasswords();
        for(IdAndPasswordsDTO idAndPasswordsDTO:idAndPasswordsDTOS){
            idAndPasswords.put(idAndPasswordsDTO.id,idAndPasswordsDTO.password);
        }
    }
    public static EmployeeController getInstance(){
        if(instance==null)
            instance= new EmployeeController();
        return instance;
    }
    //setters
    public void setWorkers(HashMap<String, Employee> workers) {
        this.workers = workers;
    }

    public static void setInstance(EmployeeController instance) {
        EmployeeController.instance = instance;
    }

    public static void setOptionsOfRole(List<String> optionsOfRole) {
        EmployeeController.optionsOfRole = optionsOfRole;
    }

    public static void setIdAndPasswords(HashMap<String, String> idAndPasswords) {
        EmployeeController.idAndPasswords = idAndPasswords;
    }

    public static void setHumanResurceId(String humanResurceId) {
        EmployeeController.humanResurceId = humanResurceId;
    }

    public static void setShipmentManagerId(String shipmentManagerId) {
        EmployeeController.shipmentManagerId = shipmentManagerId;
    }



    public List<String> showWorkers(){
        //show workers list and their details
        List<String> workersDet=new ArrayList<>();
        for(Employee employee:workers.values()){
            workersDet.add(employee.toString());
        }
        return workersDet;
    }

    public void setBonus(String id,int bonus){
        //set bonus for a worker
        if(id==null)
            throw new IllegalArgumentException("please input valid id");
        id=id.toLowerCase();
        if(!workers.containsKey(id))
            throw new IllegalArgumentException("worker id doesnt exist at the system");
        if(bonus<0)
            throw new IllegalArgumentException("the bonus is lower than 0");
        workers.get(id).setSalaryBonus(bonus);
    }


     public double getSalary(String id,int month){
         if(id==null)
             throw new IllegalArgumentException("please input valid id");
         id=id.toLowerCase();
         if(!workers.containsKey(id))
             throw new IllegalArgumentException("worker id doesnt exist at the system");
         if(month>12||month<1)
             throw new IllegalArgumentException("worker id doesnt exist at the system");
         return workers.get(id).calculateSalary(month);
     }
    public void addNewWorker(String mid,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        if(mid==null||mname==null||mbankCompany==null||mterms==null||state==null)
            throw new IllegalArgumentException("one or more of the details isn't valid");
        mid=mid.toLowerCase();
        if(workers.containsKey(mid))
            throw new IllegalArgumentException("already has worker with that id");
        Employee employee=new Employee(mid,mname,mbankCompany,mbankBranch,mbankId,msalary,LocalDate.now(),mterms,state);
        if(employeeMapper.insert(mid,mname,mbankCompany,mbankBranch,mbankId,msalary,0,LocalDate.now(),mterms,true,state,false)) {
            if(idAndPasswordsMapper.insertIdAndPassword(mid,password)) {
                workers.put(mid, employee);
                idAndPasswords.put(mid, password);
            }
            else
                employeeMapper.delete(mid);
        }
    }

    public void addNewDriver(String mid,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,
                             String mterms,String state,String password,String licenceType,String licenceNumber,String tempControlledLicence){

        if(mid==null||mname==null||mbankCompany==null||mterms==null||state==null ||licenceType==null|| licenceNumber==null||tempControlledLicence==null)
            throw new IllegalArgumentException("one or more of the details isn't valid");
        mid=mid.toLowerCase();
        if(workers.containsKey(mid))
            throw new IllegalArgumentException("already has worker with that id");
        Employee driver=new Driver(licenceNumber,licenceType,getTempControllbyName(tempControlledLicence.toLowerCase()),mid,mname,mbankCompany,mbankBranch,mbankId,msalary,LocalDate.now(),mterms,state);
        if(employeeMapper.insert(mid,mname,mbankCompany,mbankBranch,mbankId,msalary,0,LocalDate.now(),mterms,true,state,true)) {
            if (idAndPasswordsMapper.insertIdAndPassword(mid, password)) {
                if(driverMapper.insert(mid,licenceType,licenceNumber,getTempControllbyName(tempControlledLicence.toLowerCase()))) {
                    workers.put(mid, driver);
                    idAndPasswords.put(mid, password);
                }
                else idAndPasswordsMapper.delete(mid);
                }
            else
                employeeMapper.delete(mid);
        }
    }

    private boolean getTempControllbyName(String tempControlledLicence) {
        boolean ans=false;
        tempControlledLicence = tempControlledLicence.toLowerCase();
       if(tempControlledLicence.equals("t"))
           ans=true;


        return ans;
    }

    public void removeWorker(String id){
        //remove worker from the system by setting his active as false
        if(id==null)
            throw new IllegalArgumentException("please enter valid id");
        id=id.toLowerCase();
        if(!workers.containsKey(id))
            throw new IllegalArgumentException("worker id doesnt exist at the system");
        if(employeeMapper.updateEmployee(id,"is_active",false)) {
            workers.get(id).setActive(false);
        }
    }

    public Employee getEmployee(String id){
        //get employee of the system if exist
        if(id==null)
            throw new IllegalArgumentException("please enter valid id");
        id=id.toLowerCase();
        if(workers.containsKey(id)){
            return workers.get(id);
        } 
        else
            throw new IllegalArgumentException("no such id in the system");
    }
    public void addRoleToEmployee(String id,String role){
        //add new role for worker

        if(id==null||role==null)
            throw new IllegalArgumentException("please enter valid id and role");
        id=id.toLowerCase();
        role=role.toLowerCase();
        Employee employee = getEmployee(id);
        if(employee.getLicenceType()!=null){
            throw new IllegalArgumentException("cant add role to Driver");
        }
        if(!optionsOfRole.contains(role))
            throw new IllegalArgumentException("role name is Not valid!");
        if(role.equals("d"))
            throw new IllegalArgumentException("cant add driver role");
        employee.addRole(role);
    }


    private LocalDate getDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public EmployeeType gEmployeeType(String role) {
        //help function to get employee type
        switch (role) {
            case ("shm"):
                return (EmployeeType.ShiftManager);
            case ("sm"):
                return (EmployeeType.SalesMan);
            case ("se"):
                return (EmployeeType.Security);
            case ("sk"):
                return (EmployeeType.StoreKeeper);
            case ("u"):
                return (EmployeeType.Usher);
            case ("c"):
                return (EmployeeType.Clean);
            case ("g"):
                return (EmployeeType.General);
            case ("d"):
                return (EmployeeType.Driver);
        }
        throw new IllegalArgumentException("wrong input for role");
    }

    private ShiftType getShiftType(String type) {
        //help function to get shift type
        switch (type) {
            case ("M"):
                return ShiftType.Morning;
            case ("E"):
                return ShiftType.Evening;

        }
        throw new IllegalArgumentException("wrong input for shiftType");
    }

    private static void checkDate(int year, int month ,int day){

        if(!(year>0 & month>0 & day >0 & month<13 & day<32)){
            throw new IllegalArgumentException("bad inputs for date");
        }
}
    public String login(String id,String password){
        //login to system
        if (id==null|password==null)
            throw new IllegalArgumentException("please enter valid id and password");
        id=id.toLowerCase();
        Employee employee=getEmployee(id);
        if(!idAndPasswords.get(id).equals(password))
            throw new IllegalArgumentException("password is incorrect");
        return employee.login();
    }
    public void logout(String id){
        //logout from system
        if (id==null)
            throw new IllegalArgumentException("please enter valid id");
        id=id.toLowerCase();
        Employee employee=getEmployee(id);
        employee.logout();
    }
    public void humanResourceManagerRegister(String id,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        if (id==null|password==null|mbankCompany==null|state==null)
            throw new IllegalArgumentException("please enter fields");
        id=id.toLowerCase();
        if(humanResurceId!=null)
            throw new IllegalCallerException("human resource manager already registered");
        if(employeeRolesMapper.addRoleToEmp(id,"hr")) {
            humanResurceId = id;
            addNewWorker(id, mname, mbankCompany, mbankBranch, mbankId, msalary, mterms, state, password);
        }
    }

    public void shipmentManagerRegister(String id,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        if (id==null|password==null|mbankCompany==null|state==null)
            throw new IllegalArgumentException("please enter fields");
        id=id.toLowerCase();
        if(shipmentManagerId!=null)
            throw new IllegalCallerException("shipment manager already registered");
        if(employeeRolesMapper.addRoleToEmp(id,"sMan")) {
            shipmentManagerId = id;
            addNewWorker(id, mname, mbankCompany, mbankBranch, mbankId, msalary, mterms, state, password);
        }
    }


    public String getHumanResurceId(){
        if(humanResurceId==null)
            throw new NoSuchElementException("system hasnt activated by manager");
        return humanResurceId;

    }
    public void updateState(String id,String state){
        if(id==null)
            throw new IllegalArgumentException("please enter valid id");
        if(state==null)
            throw new IllegalArgumentException("null state isnt valid");
        if(!workers.containsKey(id)){
            throw new IllegalArgumentException("worker not exist");
        }
        getEmployee(id).setPersonalState(state);
    }




    public String getShipmentManagerId() {
        return shipmentManagerId;
    }
    public void clearForTest(){
        instance=null;
        employeeMapper.deleteAll();
        employeeMapper.deleteAll();
        idAndPasswordsMapper.deleteAll();
        driverMapper.deleteAll();
        employeeRolesMapper.deleteAll();

    }
    public void setBank(String empId,String bankCompany, int bankBranch, int bankId){
        if(empId ==null || bankCompany==null){
            throw new IllegalArgumentException("id and bank company cant be null");
        }
        getEmployee(empId).setBank(bankCompany,bankBranch,bankId);
    }

    public void setTerms(String empId,String terms){
        if(terms==null || empId==null){
            throw new IllegalArgumentException("terms cant be null");

        }
        getEmployee(empId).setTerms(terms);
    }
    public void setSalary(String empId,int salary){
        if( empId==null){
            throw new IllegalArgumentException("id cant be null");
        }

        getEmployee(empId).setSalary(salary);
    }

    public void setLisenceNumber(String driverId, String lisenceNumber){
        Employee driver=getDriver(driverId);
        if(driver instanceof Driver){
        if(driverMapper.updateDriver(driverId,"licence_number",lisenceNumber)){
        ((Driver) driver).setLicenceNumber(lisenceNumber);
        }
        }
    }
    public void setLisenceType(String driverId,String type){
        IDriver.LicenceType type1=null;
            switch (type){
                case "B":
                    type1 = IDriver.LicenceType.B;
                    break;
                case "C":
                    type1 = IDriver.LicenceType.C;
                    break;
                case "C1":
                    type1 = IDriver.LicenceType.C1;
                    break;

            }
            if(type1==(null)){
                throw new IllegalArgumentException("bad license type");

            }


        Employee driver=getDriver(driverId);
        if(driver instanceof Driver){
            if(driverMapper.updateDriver(driverId,"licence_type",type1.toString())){
                ((Driver) driver).setLicenceType(type);
            }
        }
    }
    public void setTempControlled(String driverId,String controll){
        boolean ans;
        if(controll.equals("T"))
            ans=true;
        else
            ans=false;
        Employee driver=getEmployee(driverId);
        if(driver instanceof Driver){
            if(driverMapper.updateDriver(driverId,"temp_controlled_licence",controll)){
                ((Driver) driver).setTempControlled(ans);
            }
        }
    }
    public Driver getDriver(String id){
        Driver driver= (Driver) workers.get(id);
        return driver;
    }
    public List<Employee> getEmployees(){
        return workers.values().stream().toList();
    }

    public ArrayList<String> getWorkersIdName() {
        ArrayList<String> ans = new ArrayList<>();
        List<Employee> emps = getEmployees();
        for (Employee emp : emps) {
            ans.add(emp.id);
        }
    return ans;
    }


}

