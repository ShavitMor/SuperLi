package ServiceLayer;

import BusinessLayer.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import BusinessLayer.ShiftController;
public class ShiftService {


    private static ShiftController shiftController;
    private static EmployeeController employeeController;
    public ShiftService(){
        shiftController=ShiftController.getInstance();
        employeeController=EmployeeController.getInstance();
    }


    public Response showAvailableWorkersForShift(int year,int month,int day, String st){
            //show workers list that can work a shift
            try {
                List<String> res = shiftController.showAvailableWorkersForShift(year,month, day, st);
                return Response.ResponseValue(res);

            } catch (Exception ex) {
                return Response.ErrorResponse(ex);
            }

        }
    public Response addWorkerToShift(int year,int month,int day, String st,String id,String type,String branch){
        try {
            //add worker to the shift
            shiftController.AddWorkerToShift(year,month,day, st, id, type,branch);
            return  Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }

    }

    public Response showHistoryOfShifts() {
        try {
            //get a list of all shifts
            List<String> res = shiftController.getShiftHistory();
            return Response.ResponseValue(res);
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
    }


    public Response addRoleToShift(int year,int month,int day, String st,String role,int quantity,String branch){
        //add role to shift
        try {
            shiftController.addRoleToShift(year,month,day, st, role, quantity,branch);
            return  Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }

    }
    public Response shiftEvents(int year,int month,int day,String st,String branch){
        //show shift's events
        try {
            List<String> res= shiftController.showShiftEvents(year,month,day, st,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }

    }
    public Response showAvailableWorkersForShiftByRole(int year,int month,int day, String st,String role){
        //show workers list that can work a shift in selected role
        try {
        List<String> res= shiftController.showAvailableWorkersForShiftByRole(year,month,day,st,role);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response PublishShift(int year,int month,int day, String st,String branch){
        //publish shift to the workers
        try {
            shiftController.publishShift(year,month,day,st,branch);
            return  Response.Empty();
        }
        catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response addShift(int year,int month,int day,int mbegin,int shiftTime,String shiftType,String branch){
        try {
            //add new shift
            shiftController.addShift(year, month, day, shiftType,mbegin,shiftTime,branch);
            return  Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response notifyArrangement(){
        //notify to manager if arrangement isn't published for the next 24 hours
        try {
            shiftController.noticeIfNotPublishedForTomorrow();
            return  Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

    public Response getCancelationsByShift(int year ,int month,int day,String shiftType,String branch){
        try {
            List<String> res = shiftController.getCancelationByShift(year, month, day, shiftType, branch);
            return Response.ResponseValue(res);
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
    
    }
    public Response AddConstraint(String employeeID,int year,int month,int day, String st) {
        //add to the shift a worker that want to work that day
        try {
            shiftController.AddConstraint(employeeID, year, month, day, st);
            return  Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
    }
    public Response addShiftEvent(String id,int year ,int month,int day,String shiftType,String branch,String event){
        try {
            shiftController.addShiftEvent(id,year,month,day,shiftType,branch,event);
            return  Response.Empty();
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response addShiftCancellation(String id,int year ,int month,int day,String shiftType,String branch,int prodId,int quantity) {
        try {
            shiftController.addShiftCancellation(id, year, month, day, shiftType, branch, prodId,quantity);
            return  Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
    }

    public Response getFutureShipmentsForEmp(String empId){
        try {
            List<String> res= shiftController.getFutureShipmentsForEmp(empId);
            return Response.ResponseValue(res);
        }
        catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response isShiftExist(int year,int month,int day,String shiftType,String branch){
        try {
            boolean res=shiftController.isShiftExist(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getShiftBegin(int year,int month,int day, String shiftType, String branch){
        try {
            LocalTime res=shiftController.getShiftStartTime(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getShiftEnd(int year,int month,int day, String shiftType, String branch){
        try {
            LocalTime res=shiftController.getShiftEndTime(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getShiftWorkers(int year,int month,int day, String shiftType, String branch){
        try {
            List<String> res=shiftController.getShiftWorkers(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getShiftRolesNeeded(int year,int month,int day, String shiftType, String branch){
        try {
            HashMap<String,Integer> res=shiftController.getShiftRolesNeeded(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
    public Response getShiftIsPublished(int year,int month,int day, String shiftType, String branch){
        try {
            boolean res=shiftController.getShiftPublished(year,month,day,shiftType,branch);
            return Response.ResponseValue(res);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }

}
