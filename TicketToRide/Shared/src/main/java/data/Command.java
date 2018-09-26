package data;

import results.Results;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command {

    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private Object[] _paramValues;

    public Command(String className, String methodName, String[] paramTypes, Object[] paramValues) {
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
    }

    public String get_className() {
        return _className;
    }

    public void set_className(String _className) {
        this._className = _className;
    }

    public String get_methodName() {
        return _methodName;
    }

    public void set_methodName(String _methodName) {
        this._methodName = _methodName;
    }

    public String[] get_paramTypes() {
        return _paramTypes;
    }

    public void set_paramTypes(String[] _paramTypes) {
        this._paramTypes = _paramTypes;
    }

    public Object[] get_paramValues() {
        return _paramValues;
    }

    public void set_paramValues(Object[] _paramValues) {
        this._paramValues = _paramValues;
    }

    public Results execute() {
        //Results results = new Results();

        try {
            //String processor the class? yeah that makes sense.
            //And then name the method in that class to invoke.
            Class<?> receiver = Class.forName(get_className());


            //this is a round-about way of using an array of strings for class types because
            //gson cannot serialize an array of type: Class.
            Class<?>[] paramTypesArray = new Class<?>[get_paramTypes().length];
            //quickly change paramtypes into classes
            int arrayIndex = 0;
            for(String stringType: get_paramTypes()){
                switch(stringType){
                    case "String":
                        paramTypesArray[arrayIndex] = String.class;
                        break;
                }
                arrayIndex++;
            }


            Method method = receiver.getMethod(get_methodName(), paramTypesArray);

            //crap this returns a string...
            Object returnedObject = (Object)method.invoke(null, get_paramValues());

            //gotta make a Result object out of it.
            //Basically just wrap it in the Object data

            //result = new Result();
            //result.setData(returnedObject);
            //result.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return null;
        }
    }
}
