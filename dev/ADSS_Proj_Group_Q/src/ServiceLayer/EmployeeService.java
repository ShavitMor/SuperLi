package ServiceLayer;

import BusinessLayer.*;
import BusinessLayer.ShiftType;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {

    private static ShiftController shiftController;
    private static EmployeeController employeeController;

    public EmployeeService() {

        this.shiftController = ShiftController.getInstance();
        this.employeeController = EmployeeController.getInstance();
    }

    public Response getWorkersIdName(){
        try {
            List<String> res = employeeController.getWorkersIdName();
            return Response.ResponseValue(res);
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }

    }


    public Response showWorkers() {
        //show workers list and their details
        try {
            List<String> res = employeeController.showWorkers();
            return Response.ResponseValue(res);
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }

    }


    public Response showShifts(String id) {
        try {
            //return worker future shifts if published
            List<String> res = shiftController.getWorkerFutureShifts(id);
            return Response.ResponseValue(res);
        } catch (Exception e) {
            return Response.ErrorResponse(e);
        }
    }


    public Response login(String id, String password) {
        //login of employee to the system
        try {
            String name=employeeController.login(id,password);
            return Response.ResponseValue(name);
        }
        catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response logout(String id){
        //logout of employee from the system
        try {
            employeeController.logout(id);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response humanResourceManagerRegister(String id,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        //first register to the system of human resource manager;
        try {
            employeeController.humanResourceManagerRegister(id,mname,mbankCompany,mbankBranch,mbankId,msalary,mterms,state,password);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }

    public Response shipmentManagerRegister(String id,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        //first register to the system of human resource manager;
        try {
            employeeController.shipmentManagerRegister(id,mname,mbankCompany,mbankBranch,mbankId,msalary,mterms,state,password);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }


    public Response getMonthSalary(String id, int month) {
        try {
            double res=employeeController.getSalary(id, month);
            return Response.ResponseValue(res);
        } catch (Exception exception) {
            return Response.ErrorResponse(exception);
        }
    }
    public Response getHumanResourceManagerId(){
        //return get Human Resource Manager Id
        try {
            String res= employeeController.getHumanResurceId();
            return Response.ResponseValue(res);
        } catch (Exception exception) {
            return Response.ErrorResponse(exception);
        }
    }
    public Response getShipmentManagerId(){
        //return get Human Resource Manager Id
        try {
            String res= employeeController.getShipmentManagerId();
            return Response.ResponseValue(res);
        } catch (Exception exception) {
            return Response.ErrorResponse(exception);
        }
    }
    public Response setBonus(String id,int bonus){
        try{
        employeeController.setBonus(id, bonus);
            return Response.Empty();
         }
        catch(Exception e){
            return Response.ErrorResponse(e);
        }
    }
    //add driver role,add drive
    public Response addRoleToEmployee(String id,String role){
        try {
            employeeController.addRoleToEmployee(id, role);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response addNewDriver(String mid,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,
                             String mterms,String state,String password,String licenceType,String licenceNumber,String tempControlledLicence){
        try {
            employeeController.addNewDriver( mid, mname, mbankCompany, mbankBranch, mbankId, msalary,
             mterms, state, password, licenceType, licenceNumber, tempControlledLicence);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }




    public Response addNewWorker(String mid,String mname,String mbankCompany,int mbankBranch,int mbankId,double msalary,String mterms,String state,String password){
        //add new Worker to the system
        try {
            employeeController.addNewWorker(mid,mname,mbankCompany,mbankBranch,mbankId,msalary,mterms,state,password);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response removeWorker(String id){
        //remove Worker from the system
        try {
            employeeController.removeWorker(id);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response restrictEmployee(int year,int month,int day,String shiftType,String empID){
        try {
            //add new shift
            shiftController.restrictEmployee(year, month, day,shiftType ,empID);
            return Response.Empty();
        }catch (Exception exception)
        {
            return Response.ErrorResponse(exception);
        }
    }
    public Response updateState(String id, String state){
        try {
            employeeController.updateState(id,state);
            return Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response setBank(String empId,String bankCompany, int bankBranch, int bankId){
        try{
            employeeController.setBank(empId,bankCompany,bankBranch,bankId);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response setTerms(String empId, String terms){
        try{
            employeeController.setTerms(empId,terms);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSalary(String empId, int salary){
        try{
            employeeController.setSalary(empId,salary);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response setLicenceNumberString( String driverId,String type){
        try{
            employeeController.setLisenceNumber(driverId,type);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }



    public Response setLisenceType(String driverId,String type){
        try{
            employeeController.setLisenceType(driverId,type);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }

    }

    public Response setTempControlled(String driverId,String controll){
        try{
            employeeController.setTempControlled(driverId,controll);
            return Response.Empty();
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response getWorkerName(String ID){
        try {
            String res= employeeController.getEmployee(ID).getName();
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getDriverLicense(String id){
        try {
            IDriver.LicenceType res=employeeController.getEmployee(id).getLicenceType();
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getEmployees(){
        try {
            List<Employee> res=employeeController.getEmployees();
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getEmployee(String id){
        try {
            Employee res=employeeController.getEmployee(id);
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getDriver(String id){
        try {
            Employee res=employeeController.getDriver(id);
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response getEmployeeDetails(String id){
        try {
            String[] res=employeeController.getEmployeeDetails(id);
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }


    public Response getDriverDetails(String id){
        try {
            String[] res=employeeController.getDriverDetails(id);
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
}
