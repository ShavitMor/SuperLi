
package BusinessLayer;

import DataLayer.*;
import DataLayer.DTOs.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class ShiftController {


    //------------------------fields----------------------------
    private HashMap<dateAndType, ArrayList<Shift>> allShifts; //Date,ShiftType=>Shift
    private static ShiftController instance;
    private HashMap<dateAndType, List<Employee>> workersConstraints;
    private HashMap<dateAndType, List<String>> managerForbiddensForShift;
    private HashMap<LocalDate, HashMap<String, Integer>> socialForbiddensForShift;
    private HashMap<dateAndType, List<Employee>> driverShifts;
    private HashMap<dateAndType, Integer> driversNeeded;

    private ShiftMapper shiftMapper;
    private ShiftWorkerMapper shiftWorkerMapper;
    private WorkerConstraintsMapper workerConstraintsMapper;
    private WorkersForbiddensMapper forbiddensMapper;
    private DriversShiftsMapper driversShiftsMapper;
    private DriversNeededMapper driversNeededMapper;
    private SocialForbiddensMapper socialForbiddensMapper;
    private RolesNeededMapper rolesNeededMapper;
    private CancelationsMapper cancelationsMapper;
    private ShiftEventsMapper shiftEventsMapper;


    //------------------------fields----------------------------

    //------------------------singelton----------------------------
    private ShiftController() {
        allShifts = new HashMap<>();
        instance = this;
        workersConstraints = new HashMap<>();
        managerForbiddensForShift = new HashMap<>();
        socialForbiddensForShift = new HashMap<>();
        driverShifts = new HashMap<>();
        driversNeeded=new HashMap<>();
        shiftMapper = new ShiftMapper();
        shiftWorkerMapper = new ShiftWorkerMapper();
        workerConstraintsMapper = new WorkerConstraintsMapper();
        forbiddensMapper = new WorkersForbiddensMapper();
        driversShiftsMapper = new DriversShiftsMapper();
        driversNeededMapper = new DriversNeededMapper();
        socialForbiddensMapper = new SocialForbiddensMapper();
        rolesNeededMapper=new RolesNeededMapper();
        cancelationsMapper=new CancelationsMapper();
        shiftEventsMapper=new ShiftEventsMapper();

    }

    public static ShiftController getInstance() {
        if (instance == null)
            instance = new ShiftController();
        return instance;
    }
    //------------------------singelton----------------------------


    //------------------------functions----------------------------

    public void loadShift() {
        List<ShiftDTO> shiftDTOS = shiftMapper.getAllShifts();
        for (ShiftDTO shiftDTO : shiftDTOS) {
            Shift shift = new Shift(shiftDTO);
            dateAndType dateAndType = new dateAndType(shiftDTO.date, shift.getShiftType());
            if (allShifts.containsKey(dateAndType))
                allShifts.get(dateAndType).add(shift);
            else {
                ArrayList<Shift> shiftList = new ArrayList<>();
                shiftList.add(shift);
                allShifts.put(dateAndType, shiftList);
            }
        }
        List<WorkerConstraintsDTO> workerConstraintsDTOS = workerConstraintsMapper.getAllEmployeesConstraints();
        for (WorkerConstraintsDTO workerConstraintsDTO : workerConstraintsDTOS) {
            LocalDate lc = workerConstraintsDTO.date;
            ShiftType shiftType = getShiftType(workerConstraintsDTO.time);
            dateAndType dateAndType = new dateAndType(lc, shiftType);
            Employee employee = EmployeeController.getInstance().getEmployee(workerConstraintsDTO.id);
            if (workersConstraints.containsKey(dateAndType)) {
                workersConstraints.get(dateAndType).add(employee);
            } else {
                List<Employee> employeeList = new ArrayList<>();
                employeeList.add(employee);
                workersConstraints.put(dateAndType, employeeList);
            }
        }
        List<DriversNeededDTO> driversNeededDTOS = driversNeededMapper.getAllDriversNeeded();
        for (DriversNeededDTO driversNeededDTO : driversNeededDTOS) {
            LocalDate lc = driversNeededDTO.date;
            ShiftType shiftType = getShiftType(driversNeededDTO.shiftType);
            dateAndType dateAndType = new dateAndType(lc, shiftType);
            driversNeeded.put(dateAndType, driversNeededDTO.amount);
        }
        List<DriversShiftsDTO> driversShiftsDTOS = driversShiftsMapper.getAllDriverShifts();
        for (DriversShiftsDTO driversShiftsDTO : driversShiftsDTOS) {
            LocalDate lc = driversShiftsDTO.date;
            ShiftType shiftType = getShiftType(driversShiftsDTO.time);
            dateAndType dateAndType = new dateAndType(lc, shiftType);
            Employee employee = EmployeeController.getInstance().getEmployee(driversShiftsDTO.id);
            if (driverShifts.containsKey(dateAndType)) {
                driverShifts.get(dateAndType).add(employee);
            } else {
                List<Employee> employeeList = new ArrayList<>();
                employeeList.add(employee);
                driverShifts.put(dateAndType, employeeList);
            }
        }
        List<WorkersForbiddensDTO> workersForbiddensDTOS = forbiddensMapper.getMannagerForbidens();
        for (WorkersForbiddensDTO workersForbiddensDTO : workersForbiddensDTOS) {
            LocalDate lc = workersForbiddensDTO.date;
            ShiftType shiftType = getShiftType(workersForbiddensDTO.time);
            dateAndType dateAndType = new dateAndType(lc, shiftType);
            if (managerForbiddensForShift.containsKey(dateAndType)) {
                managerForbiddensForShift.get(dateAndType).add(workersForbiddensDTO.id);
            } else {
                List<String> list = new ArrayList<>();
                list.add(workersForbiddensDTO.id);
                managerForbiddensForShift.put(dateAndType, list);
            }
        }
        List<SocialForbiddensDTO> workersSocialForbiddensDTOS = socialForbiddensMapper.getAllSocialForbiddens();
        for (SocialForbiddensDTO socialForbiddensDTO : workersSocialForbiddensDTOS) {
            LocalDate lc = socialForbiddensDTO.date;
            if (socialForbiddensForShift.containsKey(lc)) {
                socialForbiddensForShift.get(lc).put(socialForbiddensDTO.empId, socialForbiddensDTO.amount);
            } else {
                HashMap<String, Integer> socialFor = new HashMap<>();
                socialFor.put(socialForbiddensDTO.empId, socialForbiddensDTO.amount);
                socialForbiddensForShift.put(lc, socialFor);
            }
        }

    }

    public HashMap<dateAndType, List<Employee>> getWorkersConstraints() {
        // return constraints of workers
        return workersConstraints;
    }

    public List<String> showAvailableWorkersForShift(int year, int month, int day, String shiftType) {
        //show workers list that can work a shift
        if (shiftType == null)
            throw new IllegalArgumentException("please enter valid shift type m/e");
        shiftType = shiftType.toUpperCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType st = getShiftType(shiftType);
        dateAndType dateAndType = new dateAndType(date, st);
        if (!allShifts.containsKey(dateAndType))
            throw new IllegalArgumentException("date hasnt opened yet");
        if(workersConstraints.get(dateAndType)==null)
            throw new NoSuchElementException("dont have any workers");
        List<String> aviEmployees = workersConstraints.get(dateAndType).stream().map(Employee::getDetAndRoles).collect(Collectors.toList());
        return aviEmployees;
    }

    public void AddWorkerToShift(int year, int month, int day, String type, String employee1, String role, String branch1) {
        //add worker to the shift
        //add worker to the shift
        checkDate(year, month, day);
        ShiftType st = getShiftType(type);
        EmployeeType employeeType = gEmployeeType(role);
        Employee employee = EmployeeController.getInstance().getEmployee(employee1);
        List<EmployeeType> employeeRoles = employee.getEmployeeTypes();
        if (!employeeRoles.contains(employeeType)) {
            throw new IllegalArgumentException("the employee cant work in this role");
        }
        LocalDate date = getDate(year, month, day);
        dateAndType dateAndType = new dateAndType(date, st);
        if (!allShifts.containsKey(dateAndType))
            throw new IllegalArgumentException("date hasnt opened yet");
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);

        Shift shift = null;
        ArrayList<Shift> shifts = allShifts.get(dateAndType);
        for (Shift s : shifts) {
            if (s.getBranch().equals(branch)) {
                shift = s;
            }
        }
        boolean isAvi = false;
        if(!workersConstraints.containsKey(dateAndType))
            throw new IllegalStateException("the employee cant work this shift");
        for (Employee emp : workersConstraints.get(dateAndType)) {
            if (emp.equals(employee)) {
                isAvi = true;
            }
        }
        if (!isAvi) {
            throw new IllegalArgumentException("the employee cant work this shift");
        }

        if (!(shift.getRolesNeeded().containsKey(employeeType) || shift.getRolesNeeded().get(employeeType) > 0)) {
            throw new IllegalArgumentException("there is no space for the worker to do this work");
        }
        if (employeeType == employeeType.ShiftManager) {
            if (shift.getShiftManagerID() != null) {
                throw new IllegalArgumentException("already have manager");
            }
            else{
                 shift.setshiftManagerID(employee1);
            }
        }
        for (Shift shift1 : allShifts.get(dateAndType)) {
            if (shift1.isWork(employee)) {
                throw new IllegalArgumentException("worker already works this time");
            }
        }

        if (driverShifts.get(dateAndType) != null && driverShifts.get(dateAndType).contains(employee))
            throw new IllegalArgumentException("worker already works this time");


        shift.addWorkerToShift(employee, employeeType);
        shift.setRoleToShift(employeeType, shift.getRolesNeeded().get(employeeType) - 1);
        if(isStoreKeeperInDay(branch.name,date))
            ShippingManager.getInstance().updateShipmentSiteError(date,branch);
        //add the hours worker work
    }

    public void addDriverToShift(String driver1, LocalDate date, LocalTime time) {
        //add driver to shift according to shipment
        Employee driver = EmployeeController.getInstance().getEmployee(driver1);
        ShiftType st = getTypeByTime(time);
        dateAndType dateAndType = new dateAndType(date, st);
        if(!driver.isActive())
            throw new IllegalArgumentException("driver isnt active");
        if (driverShifts.get(dateAndType) != null && driverShifts.get(dateAndType).contains(driver))
            throw new IllegalArgumentException("worker already works this time");

        boolean isAvi = false;
        for (Employee emp : workersConstraints.get(dateAndType)) {
            if (emp.equals(driver)) {
                isAvi = true;
            }
        }
        if (!isAvi) {
            throw new IllegalArgumentException("the driver cant work this shift");
        }

        if (!(driversNeeded.containsKey(dateAndType))) {
            throw new IllegalArgumentException("there is no driver need in this shift");
        } else {
            if (driversNeeded.get(dateAndType) <= 0) {
                throw new IllegalArgumentException("there is no space for the worker to do this work");
            }
            if (driverShifts.containsKey(dateAndType)) {
                if (driversShiftsMapper.insert(date, st.toString(), driver.id)) {
                    driverShifts.get(dateAndType).add(driver);
                }
            } else {

                if (driversShiftsMapper.insert(date, st.toString(), driver.id)) {
                    driverShifts.put(dateAndType, new ArrayList<>());
                    driverShifts.get(dateAndType).add(driver);
                }
            }
        }
    }

    public void removeDriverFromShift(String driver1, LocalDate date, LocalTime time) {
        //add driver to shift according to shipment
        Employee driver = EmployeeController.getInstance().getEmployee(driver1);
        if(date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("shift already passed");
        ShiftType st = getTypeByTime(time);
        dateAndType dateAndType = new dateAndType(date, st);
        if (driverShifts.get(dateAndType) == null || !driverShifts.get(dateAndType).contains(driver))
            throw new IllegalArgumentException("driver doesnt work");
        if(driversNeeded.get(dateAndType)==null){
            if(driversShiftsMapper.delete(date,st.toString(),driver1)) {
               // driversNeeded.put(dateAndType, 1);
                driverShifts.get(dateAndType).remove(driver);
            }
        }
        else {
            if(driversShiftsMapper.delete(date,st.toString(),driver1)){
               // driversNeeded.put(dateAndType,driversNeeded.get(dateAndType)+1);
            driverShifts.get(dateAndType).remove(driver);
        }
        }
//driversNeededMapper.updateDriversNeeded(date,st.toString(),driversNeeded.get(dateAndType)+1)&&
    }
    public void AddConstraint(String employee1, int year, int month, int day, String Type) {
        //add constraint of worker
        Employee employee = EmployeeController.getInstance().getEmployee(employee1);

        if (!employee.isActive())
            throw new IllegalStateException("your profile is inactive");
        if (Type == null)
            throw new IllegalArgumentException("please enter valid shift type");
        Type = Type.toUpperCase();
        checkDate(year, month, day);
        ShiftType stype = getShiftType(Type);
        LocalDate date = getDate(year, month, day);
        dateAndType currShift = new dateAndType(date, stype);
        if (!allShifts.containsKey(currShift)&&!driversNeeded.containsKey(currShift))
            throw new IllegalArgumentException("shift hasn't opened yet");
        if(workersConstraints.get(currShift)!=null&&workersConstraints.get(currShift).contains(employee))
            throw new IllegalStateException("you already asked this shift");
        ArrayList<Shift> shifts = allShifts.get(currShift);
        boolean isRole = false;
        if(shifts!=null) {
            for (Shift shift : shifts) {
                for (EmployeeType employeeType : shift.getRolesNeeded().keySet())
                    if (employee.isContainRole(employeeType))
                        isRole = true;
            }
        }
        if (employee.getEmployeeTypes().contains(EmployeeType.Driver) && driversNeeded.containsKey(currShift) && driversNeeded.get(currShift) > 0) {
            isRole = true;
        }
        if (!isRole)
            throw new NoSuchElementException("there is no role for you at this date for now");
        List<String> managerForbiden = managerForbiddensForShift.get(currShift);
        if (managerForbiden != null && !managerForbiden.isEmpty() && managerForbiden.contains(employee.getId()))
            throw new IllegalStateException("manager forbidden you to work that day");
        if (isEmployeeAskedThatDay(date, employee))
            throw new IllegalStateException("you already asked to work this day");
        int sunday = date.getDayOfWeek().getValue();
        LocalDate lc = date.minusDays(sunday);
        HashMap<String, Integer> shiftsOfWeek = socialForbiddensForShift.get(lc);
        if (shiftsOfWeek != null && shiftsOfWeek.containsKey(employee.getId()) && shiftsOfWeek.get(employee.getId()) >= 6)
            throw new IllegalStateException("you asked shifts beyond social rules, please remove shifts before trying again");

        if (workerConstraintsMapper.insertWorkerConstraints(employee.id, date, stype.toString())) {

            if (!workersConstraints.containsKey(currShift)) {
                List<Employee> list = new ArrayList<>();
                list.add(employee);
                workersConstraints.put(currShift, list);
            } else {
                workersConstraints.get(currShift).add(employee);
            }


            if (shiftsOfWeek == null) {
                if (socialForbiddensMapper.insertWorkeSocialrForbidden(lc, employee.id, 1)) {
                    shiftsOfWeek = new HashMap<>();
                    shiftsOfWeek.put(employee.getId(), 1);
                    socialForbiddensForShift.put(lc, shiftsOfWeek);
                }
            } else {
                if (shiftsOfWeek.containsKey(employee.getId())) {
                    if (socialForbiddensMapper.updateSocialForBid(lc, employee.id, shiftsOfWeek.get(employee.getId()) + 1)) {
                        socialForbiddensForShift.get(lc).put(employee.getId(), shiftsOfWeek.get(employee.getId()) + 1);
                    } else {
                        workerConstraintsMapper.deleteConstraints(employee.id, date, stype.toString());

                    }
                } else {
                    if (socialForbiddensMapper.insertWorkeSocialrForbidden(lc, employee.id, 1)) {
                        shiftsOfWeek.put(employee.getId(), 1);
                        socialForbiddensForShift.put(lc, shiftsOfWeek);
                    } else {
                        workerConstraintsMapper.deleteConstraints(employee.id, date, stype.toString());

                    }
                }
            }
        }

    }

    //the shipiing manager can use it
    public void addRoleToShift(int year, int month, int day, String Type, String emptype, int quantity, String branch) {
        // add role to the shift
        if (emptype == null | branch == null)
            throw new IllegalArgumentException("please enter valid employee type and branch");
        branch = branch.toLowerCase();
        emptype = emptype.toLowerCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType st = getShiftType(Type);
        EmployeeType role = gEmployeeType(emptype);
        dateAndType p = new dateAndType(date, st);
        ArrayList<Shift> shifts = allShifts.get(p);
        Shift shift = getShiftByBranch(p, branch);
        shift.setRoleToShift(role, quantity);

    }

    public void addDriverNeededToShift(LocalDate date,LocalTime time) {
        //require driver for shift
        ShiftType st = getTypeByTime(time);
        dateAndType p = new dateAndType(date, st);

        if (driversNeeded.containsKey(p)) {
            if (driversNeededMapper.updateDriversNeeded(date, st.toString(),driversNeeded.get(p) + 1)) {
                driversNeeded.put(p, driversNeeded.get(p) + 1);
            }
        } else {
            if (driversNeededMapper.insertDriversNeeded(date, st.toString(), 1)) {
                driversNeeded.put(p, 1);
            }
        }
    }
    public void removeDriverNeededToShift(LocalDate date,LocalTime time) {
        //remove required driver for shift
        ShiftType st = getTypeByTime(time);
        dateAndType p = new dateAndType(date, st);
        if(!driversNeeded.containsKey(p))
            throw new IllegalStateException("no1 drivers required this shift");
        if(driversNeeded.get(p)==0)
            throw new IllegalStateException("no drivers required this shift");
            if (driversNeededMapper.updateDriversNeeded(date, st.toString(),driversNeeded.get(p) - 1)) {
                driversNeeded.put(p, driversNeeded.get(p) - 1);
            }
        }




    private boolean isEmployeeAskedThatDay(LocalDate lc, Employee emp) {
        //returns if employee want to work that day
        dateAndType dateAndType = new dateAndType(lc, ShiftType.Morning);
        List<Employee> shiftsOnMorning = workersConstraints.get(dateAndType);
        if (shiftsOnMorning != null && shiftsOnMorning.contains(emp))
            return true;
        dateAndType = new dateAndType(lc, ShiftType.Evening);
        List<Employee> shiftsOnEvening = workersConstraints.get(dateAndType);
        if (shiftsOnEvening != null && shiftsOnEvening.contains(emp))
            return true;
        return false;
    }

    public List<String> showShiftEvents(int year, int month, int day, String Type, String branch) {
        //return special events
        if (branch == null)
            throw new IllegalArgumentException("please enter valid branch");
        branch = branch.toLowerCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType st = getShiftType(Type);
        dateAndType dateAndType = new dateAndType(date, st);
        if (!allShifts.containsKey(dateAndType))
            throw new IllegalArgumentException("shift isnot exist");
        dateAndType p = new dateAndType(date, st);
        ArrayList<Shift> shifts = allShifts.get(p);
        Shift shift = getShiftByBranch(p, branch);
        return shift.getSpecialEvents();
    }


    public void restrictEmployee(int year, int month, int day, String shiftType, String employee1) {
        //restrict Employee to work a shift
        Employee employee = EmployeeController.getInstance().getEmployee(employee1);

        checkDate(year, month, day);
        if (shiftType == null)
            throw new IllegalArgumentException("please enter valid shift type");

        shiftType = shiftType.toUpperCase();
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);

        dateAndType restrict = new dateAndType(date, type);
        if(managerForbiddensForShift.containsKey(restrict)&&managerForbiddensForShift.get(restrict).contains(employee1)){
            throw new IllegalArgumentException("worker has already restricted this shift");

        }
        if(workersConstraints.containsKey(restrict)&& workersConstraints.get(restrict).contains(employee))
            throw new IllegalArgumentException("worker already asked that shift");
        if (managerForbiddensForShift.containsKey(restrict)) {
            if (forbiddensMapper.insertMannagerForbidens(date, type.toString(), employee.getId())) {
                managerForbiddensForShift.get(restrict).add(employee.getId());
            }} else {
            if (forbiddensMapper.insertMannagerForbidens(date, type.toString(), employee.getId())) {
                List<String> temp = new ArrayList<>();
                temp.add(employee.getId());
                managerForbiddensForShift.put(restrict, temp);
            }
            }


    }

    public HashMap<dateAndType, ArrayList<Shift>> getShifts() {
        return allShifts;
    }


    public List<String> showAvailableWorkersForShiftByRole(int year, int month, int day, String Type, String emptype) {
        //show workers list that can work a shift in selected role
        if (Type == null)
            throw new IllegalArgumentException("please enter valid shift type");
        Type = Type.toUpperCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType st = getShiftType(Type);
        dateAndType dateAndType = new dateAndType(date, st);
        if (!allShifts.containsKey(dateAndType))
            throw new IllegalArgumentException("shift hasnt opened yet");
        EmployeeType role = gEmployeeType(emptype);
        dateAndType p = new dateAndType(date, st);
        List<Employee> employees = workersConstraints.get(p);
        ArrayList<String> aviableEmpsByRole = new ArrayList<>();
        for (Employee emp : employees) {
            List<EmployeeType> empRoles = EmployeeController.getInstance().getEmployee(emp.id).roles;
            if (empRoles.contains(role)) {
                aviableEmpsByRole.add(emp.getId());
            }
        }
        return aviableEmpsByRole;
    }


    public List<String> getShiftHistory() {
        //show all history in the system
        List<String> ret = new ArrayList<>();

        for (ArrayList<Shift> shifts : allShifts.values()) {
            for (Shift shift : shifts) {
                ret.add(shift.toString());
            }
        }
        return ret;
    }


    public LocalDate getDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    private EmployeeType gEmployeeType(String role) {
        //help function to get employee type by string
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

    public ShiftType getShiftType(String type) {
        //return shift type
        type = type.toUpperCase();
        switch (type) {
            case ("M"):
                return ShiftType.Morning;
            case ("E"):
                return ShiftType.Evening;

        }
        throw new IllegalArgumentException("wrong input for shiftType");
    }

    public List<String> getWorkerFutureShifts(String employee1) {
        //return worker future shifts if published
        Employee employee = EmployeeController.getInstance().getEmployee(employee1);

        List<String> shiftsTimes = new ArrayList<>();
        for (ArrayList<Shift> shifts : allShifts.values()) {
            for (Shift shift : shifts) {
                if ((shift.getDate().isAfter(LocalDate.now())||shift.getDate().equals(LocalDate.now())) && shift.getIsPublished()) {
                    String temp = shift.getShiftWorker(employee);
                    if (temp != null)
                        shiftsTimes.add(temp);
                }
            }
        }

        for (dateAndType shift : driverShifts.keySet()) {
            if (driverShifts.get(shift).contains(employee)) {
                if (LocalDate.now().equals(shift.date())||shift.date().isAfter(LocalDate.now())) {
                    String s = "Date: " + shift.date()+" on: "+ shift.type().name()+ " as Driver";
                    shiftsTimes.add(s);
                }
            }
        }

        if (shiftsTimes.isEmpty())
            shiftsTimes.add("you dont have any future shifts for now");

        return shiftsTimes;
    }

    public void publishShift(int year, int month, int day, String shiftType1, String branch1) {
        //publish shift wo workers
        if (shiftType1 == null || branch1 == null)
            throw new IllegalArgumentException("please enter valid shift type and branch");
        shiftType1 = shiftType1.toUpperCase();
        branch1 = branch1.toLowerCase();
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);
        checkDate(year, month, day);
        LocalDate localDate = LocalDate.of(year, month, day);
        ShiftType shiftType = getShiftType(shiftType1);
        dateAndType temp = new dateAndType(localDate, shiftType);
        boolean worked = false;
        if (!allShifts.containsKey(temp))
            throw new IllegalArgumentException("shift hasnt opened yet");
        ArrayList<Shift> shifts = allShifts.get(temp);
        for (Shift shift : shifts) {
            if (shift.getBranch().equals(branch)) {
                shift.setPublished();
                worked = true;
            }
        }
        if (!worked)
            throw new IllegalArgumentException("shift isnt exist");
    }

    public void addShift(int year, int month, int day, String shiftType, int mbegin, int shiftTime, String branch) {
        // add new shift
        if (shiftType == null || branch == null)
            throw new IllegalArgumentException("please enter valid shift type and branch");
        shiftType = shiftType.toUpperCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);
        dateAndType dateAndTime = new dateAndType(date, type);
        if (isHaveShift(dateAndTime, branch)) {
            throw new IllegalArgumentException("already opened shift in this time");
        }

        Site theBranch = SiteManager.getInstance().getFirstMatchingBranchByName(branch);
        Shift shift = new Shift(date, type, mbegin, shiftTime, theBranch);
        LocalTime begin = LocalTime.of(mbegin, 0);
        LocalTime end1 = LocalTime.of(begin.getHour() + shiftTime, begin.getMinute(), 0);
        if (shiftMapper.insertShift(date, begin, shiftTime, end1, shiftType, branch, null, false)) {
            if (!allShifts.containsKey(dateAndTime)) {
                ArrayList<Shift> updatedShifts = new ArrayList<Shift>();
                updatedShifts.add(shift);
                allShifts.put(dateAndTime, updatedShifts);
                workersConstraints.put(dateAndTime, new ArrayList<>());
            } else {
                ArrayList<Shift> updatedShifts = allShifts.get(dateAndTime);
                updatedShifts.add(shift);
                allShifts.put(dateAndTime, updatedShifts);
                workersConstraints.put(dateAndTime, new ArrayList<>());
            }
        }


    }

    public void removeShift(int year, int month, int day, String shiftType, String branch1) {
        //delete shift
        if (shiftType == null || branch1 == null)
            throw new IllegalArgumentException("please enter valid shift type and branch");
        shiftType = shiftType.toUpperCase();
        branch1 = branch1.toLowerCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);
        dateAndType dateAndTime = new dateAndType(date, type);
        if (!allShifts.containsKey(dateAndTime)) {
            throw new IllegalArgumentException("there is no submited shift in this time");
        }
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);

        ArrayList<Shift> shiftsOfday = allShifts.get(dateAndTime);
        Shift del = null;
        for (Shift shift : shiftsOfday) {
            if (shift.getBranch().equals(branch)) {
                del = shift;
            }
        }
        if ((shiftMapper.removeShift(date, shiftType, branch1))) {
            if (del != null) {
                shiftsOfday.remove(del);
                if (shiftsOfday.isEmpty()) {
                    shiftsOfday.remove(dateAndTime);
                    if (workerConstraintsMapper.deleteAllConstraints(date, shiftType)) {
                        workersConstraints.remove(dateAndTime);
                    }

                } else {
                    allShifts.put(dateAndTime, shiftsOfday);
                }
            } else
                throw new IllegalArgumentException("shift doesnt exist");
        }

    }

    public void checkDate(int year, int month, int day) {
        // check if input date is valid
        if (!(year > 0 & month > 0 & day > 0 & month < 13 & day < 32)) {
            throw new IllegalArgumentException("bad inputs for date");
        }
    }

    public Shift getShiftByBranch(dateAndType p, String branch1) {
        //get shift by branch from system
        Shift shift = null;
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);
        ArrayList<Shift> shifts = allShifts.get(p);
        if(shifts==null)
            throw new IllegalArgumentException("not have shift this day");
        for (Shift s : shifts) {
            if (s.getBranch().equals(branch)) {
                shift = s;
            }
        }
        if (shift == null) {
            throw new IllegalArgumentException("there is no shift in this branch in this time");
        }

        return shift;
    }

    public void noticeIfNotPublishedForTomorrow() {
        // notify to the manager if there isn't published arrangement for tomorrow
        LocalDate localDate = LocalDate.now();
        dateAndType morning = new dateAndType(localDate, ShiftType.Morning);
        dateAndType evening = new dateAndType(localDate, ShiftType.Evening);
        if (!allShifts.containsKey(morning) || !allShifts.containsKey(evening))
            throw new IllegalStateException("notice! arrangent isnt published for today");
        ArrayList<Shift> shiftsForToday = allShifts.get(morning);
        for (Shift shift : shiftsForToday) {
            if (!shift.getIsPublished())
                throw new IllegalStateException("notice! arrangent isnt published for today for all branches");
        }
        shiftsForToday = allShifts.get(evening);
        for (Shift shift : shiftsForToday) {
            if (!shift.getIsPublished())
                throw new IllegalStateException("notice! arrangent isnt published for today for all branches");
        }
        localDate = localDate.plusDays(1);
        morning = new dateAndType(localDate, ShiftType.Morning);
        evening = new dateAndType(localDate, ShiftType.Evening);
        ArrayList<Shift> shiftsForTomorrow = allShifts.get(morning);
        if (!allShifts.containsKey(morning) || !allShifts.containsKey(morning))
            throw new IllegalStateException("notice! arrangent isnt published for tommrow");
        for (Shift shift : shiftsForTomorrow) {
            if (!shift.getIsPublished())
                throw new IllegalStateException("notice! arrangent isnt published for tomorrow for all branches");
        }
        shiftsForTomorrow = allShifts.get(evening);
        for (Shift shift : shiftsForTomorrow) {
            if (!shift.getIsPublished())
                throw new IllegalStateException("notice! arrangent isnt published for tomorrow for all branches");
        }
    }

    public List<String> getCancelationByShift(int year, int month, int day, String shiftType, String branch) {
        //return cancellations of shift
        if (shiftType == null || branch == null)
            throw new IllegalArgumentException("please enter valid shift type and branch");
        shiftType = shiftType.toUpperCase();
        branch = branch.toLowerCase();
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);
        dateAndType dateAndTime = new dateAndType(date, type);
        Shift shift = getShiftByBranch(dateAndTime, branch);
        return shift.getCancelation();
    }

    public void addShiftEvent(String id, int year, int month, int day, String shiftType, String branch1, String event) {
        //add special event to the shift
        if (shiftType == null || event == null || branch1 == null)
            throw new IllegalArgumentException("please enter shift type and event that aren't empty");
        checkDate(year, month, day);
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);
        dateAndType temp = new dateAndType(date, type);
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);
        if (!allShifts.containsKey(temp))
            throw new NoSuchElementException("shift doesnt exist in system");
        List<Shift> shifts = allShifts.get(temp);
        boolean isFound = false;
        for (Shift shift : shifts) {
            if (shift.getBranch().equals(branch)) {
                isFound = true;
                if (!shift.getShiftManagerID().equals(id))
                    throw new IllegalStateException("only shift manager can add event to shift");
                shift.addShiftEvent(event);
            }
        }
        if (!isFound)
            throw new NoSuchElementException("shift doesnt exist in system");

    }

    public void addShiftCancellation(String id, int year, int month, int day, String shiftType, String branch1, int prodId, int quantity) {
        //add new cancellation to shift
        if (shiftType == null || branch1 == null)
            throw new IllegalArgumentException("please enter shift type and event that aren't empty");
        checkDate(year, month, day);
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);
        LocalDate date = getDate(year, month, day);
        ShiftType type = getShiftType(shiftType);
        dateAndType temp = new dateAndType(date, type);
        if (!allShifts.containsKey(temp))
            throw new NoSuchElementException("shift doesnt exist in system");
        List<Shift> shifts = allShifts.get(temp);
        boolean isFound = false;
        for (Shift shift : shifts) {
            if (shift.getBranch().equals(branch)) {
                isFound = true;
                if (!shift.getShiftManagerID().equals(id))
                    throw new IllegalStateException("only shift manager can cancellation event to shift");
                shift.addShiftCancelation(prodId, quantity);
            }
        }
        if (!isFound)
            throw new NoSuchElementException("shift doesnt exist in system");
    }

    private boolean isHaveShift(dateAndType dateAndType, String branch1) {
        //help function to check if shift is exist
        if (!allShifts.containsKey(dateAndType))
            return false;
        Site branch = SiteManager.getInstance().getFirstMatchingBranchByName(branch1);
        for (Shift shift : allShifts.get(dateAndType)) {
            if (shift.getBranch().equals(branch))
                return true;
        }
        return false;
    }

    public boolean isStoreKeeperInDay(String branch, LocalDate date) {
        //check if there store keeper in the branch that day.
        dateAndType morning = new dateAndType(date, ShiftType.Morning);
        dateAndType evening = new dateAndType(date, ShiftType.Evening);
        if (isHaveShift(morning, branch) && isHaveShift(evening, branch)) {
            if (getShiftByBranch(morning, branch).isHaveStoreKeeper() && getShiftByBranch(evening, branch).isHaveStoreKeeper()) {
                return true;
            }
        }
        return false;

    }

    public int getHours(Employee emp, int month) {
        int hours = 0;
        if (emp.roles.contains(EmployeeType.Driver)) {
            for (dateAndType p : driverShifts.keySet()) {
                LocalDate date = p.date();
                if (date.isBefore(LocalDate.now()) && date.getMonth().getValue() == month) {
                    if (driverShifts.get(p).contains(emp)) {
                        for (Shift s : allShifts.get(p)) {
                            for (List<Employee> a : s.getWorkersinShift().values()) {
                                if (a.contains(emp)) {
                                    hours = hours + s.getShiftTime();
                                }
                            }
                        }
                    }
                }
            }

        } else {
            for (dateAndType p : allShifts.keySet()) {
                LocalDate date = p.date();
                if (date.isBefore(LocalDate.now()) && date.getMonth().getValue() == month) {
                        for (Shift s : allShifts.get(p)) {
                            for (List<Employee> a : s.getWorkersinShift().values()) {
                                if (a.contains(emp)) {
                                    hours = hours + s.getShiftTime();
                                }
                            }
                        }
                    
                }
            }


        }
        return hours;
    }

    //return the Shipments the employeee should get as Manager or StoreKeeper.
    public List<String> getFutureShipmentsForEmp(String empId){

        List<String> lis=new ArrayList<>();
        Employee emp=EmployeeController.getInstance().getEmployee(empId);
        if(!(emp.roles.contains(EmployeeType.ShiftManager) || emp.roles.contains(EmployeeType.StoreKeeper))){
            throw new IllegalStateException("only storekeeper or shiftmanager can watch their future shipments");
        }
        for(List<Shift> shifts : allShifts.values()){
            for(Shift shift :shifts){
                if((shift.getDate().isAfter(LocalDate.now())||shift.getDate().equals(LocalDate.now())) && shift.getIsPublished()){
                    if(shift.isStoreKeeperOrShiftManager(emp)){
                        String s=ShippingManager.getInstance().getSiteDeliveryStringOnDate(shift.getBranch(), shift.getDate());
                        if(!s.equals(""))
                            lis.add(ShippingManager.getInstance().getSiteDeliveryStringOnDate(shift.getBranch(), shift.getDate()));
                    }
                }
            }

        }
        if(lis.isEmpty())
            lis.add("you dont have any shipments soon");
        return lis;
    }
    public List<Driver> availableDrivers(IDriver.LicenceType licenceType,boolean tempControl,LocalDate lc,LocalTime hour){
        //return drivers  by shift and conditions
        List<Driver> employees=new ArrayList<>();
        if(lc == null || hour == null ){
            return employees;
        }
        ShiftType type=getTypeByTime(hour);
        dateAndType day=new dateAndType(lc,type);
        List<Employee> morCon=workersConstraints.get(day);
        for(Employee employee:morCon){
            if(employee.getLicenceType()!=null&& ((Driver)employee).isDriverCanDrive(licenceType)){
                if(((Driver) employee).isTempControlMatch(tempControl))
                    employees.add((Driver) employee);
            }
        }

        return employees;
    }

    public void clearForTest() {
        instance = null;
        shiftMapper.deleteAll();
        shiftWorkerMapper.deleteAll();
        workerConstraintsMapper.deleteAll();
        driversShiftsMapper.deleteAll();
        socialForbiddensMapper.deleteAll();
        forbiddensMapper.deleteAll();
        rolesNeededMapper.deleteAll();
        cancelationsMapper.deleteAll();
        driversNeededMapper.deleteAll();
        shiftEventsMapper.deleteAll();


    }
    private ShiftType getTypeByTime(LocalTime time){
        //return shift type by time for drivers
        LocalTime four=LocalTime.of(16,0);
        if(time.isBefore(four))
            return ShiftType.Morning;
        else
            return ShiftType.Evening;
    }

    public HashMap<dateAndType, Integer> getDriversNeeded() {
        return driversNeeded;
    }

    public HashMap<dateAndType, List<Employee>> getDriverShifts() {
        return driverShifts;
    }
    public boolean isShiftExist(int year,int month,int day,String shiftType,String branch){
        LocalDate lc=getDate(year,month,day);
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        return isHaveShift(dateAndType,branch);
    }
    public LocalTime getShiftStartTime(int year,int month,int day,String shiftType,String branch){
        LocalDate lc=getDate(year,month,day);
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        Shift shift=getShiftByBranch(dateAndType,branch);
        return shift.getBegin();
    }
    public LocalTime getShiftEndTime(int year,int month,int day,String shiftType,String branch){
        LocalDate lc=getDate(year,month,day);
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        Shift shift=getShiftByBranch(dateAndType,branch);
        return shift.getEnd();
    }
    public List<String> getShiftWorkers(int year,int month,int day,String shiftType,String branch){
        LocalDate lc=getDate(year,month,day);
        List<String> workers=new ArrayList<>();
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        Shift shift=getShiftByBranch(dateAndType,branch);
        for(List<Employee> list: shift.getWorkers().values())
            for(Employee employee: list){
                workers.add(employee.getId()+" "+employee.getName());
            }
           return workers;
    }
    public HashMap<String,Integer> getShiftRolesNeeded(int year,int month,int day,String shiftType,String branch){
        HashMap<String,Integer> ret=new HashMap();
        LocalDate lc=getDate(year,month,day);
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        Shift shift=getShiftByBranch(dateAndType,branch);
        HashMap<EmployeeType,Integer> roles=shift.getRolesNeeded();
        for(EmployeeType employeeType:roles.keySet()){
            ret.put(employeeType.toString(),roles.get(employeeType));
        }
        return ret;
    }
    public boolean getShiftPublished(int year,int month,int day,String shiftType,String branch){
        LocalDate lc=getDate(year,month,day);
        ShiftType shiftType1=getShiftType(shiftType);
        dateAndType dateAndType=new dateAndType(lc,shiftType1);
        Shift shift=getShiftByBranch(dateAndType,branch);
        return shift.getIsPublished();
    }
}
//------------------------functions----------------------------


