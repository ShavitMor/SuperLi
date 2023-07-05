package ServiceLayer;

public class Response {
    public String ErrorMsg =null;
    public Object ReturnValue=null;

    private Response(){};
    public static Response Empty(){
        return new Response();
    }
    public static Response ErrorResponse(Exception ex){
        Response res = new Response();
        res.ErrorMsg = ex.toString();
        return res;
    }
    public static Response ResponseValue(Object o){
        Response res = new Response();
        res.ReturnValue = o;
        return res;
    }
    public boolean isValid(){
        return ErrorMsg==null ^ ReturnValue ==null;
    }
    public boolean isValueResponse(){
        return ErrorMsg == null && ReturnValue != null;
    }
    public boolean isErrorResponse(){
        return ErrorMsg != null && ReturnValue == null;
    }
    public boolean isEmpty(){
        return ErrorMsg == null && ReturnValue ==null;
    }

    @Override
    public String toString() {
        if (isErrorResponse()){
            return ErrorMsg;
        }
        return (String)ReturnValue;
    }
}
