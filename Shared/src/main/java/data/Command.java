package data;

import com.google.gson.internal.LinkedTreeMap;

import modelclasses.User;
import results.Results;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

public class Command implements Serializable {

    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private String[] _paramValues;
    //private Serializer serializer = new Serializer();

    public Command(String className, String methodName, String[] paramTypes, String[] paramValues)
    {
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
    }

    //another simplified constructor
    public Command(String className, String methodName, List<Object> parameters){
        ArrayList<String> paramTypes = new ArrayList<>();
        ArrayList<String> _parameters = new ArrayList<>();

        Serializer serializer = new Serializer();
        for(Object object : parameters) {
            paramTypes.add(object.getClass().getName());
            String serializedObject = serializer.encode(object);
            _parameters.add(serializedObject);
        }

        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes.toArray(new String[0]);
        _paramValues = _parameters.toArray(new String[0]);
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

    public String[] get_paramValues() {
        return _paramValues;
    }

    public void set_paramValues(String[] _paramValues) {
        this._paramValues = _paramValues;
    }

    private Object getTargetInstance(Class<?> cls) {
        try {
            Method getInstanceMethod = cls.getMethod("getInstance", null);
            return getInstanceMethod.invoke(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Results execute() {

        try {
            Class<?> receiver = Class.forName(get_className());

            //this is a round-about way of using an array of strings for class types because
            //gson cannot serialize an array of type: Class.
            Class<?>[] paramTypesArray;
            String[] paramValues;

            //turn param types from string to Class
            ArrayList<Class<?>> paramTypesList = new ArrayList<>();
            for(String stringType : get_paramTypes()) {
                paramTypesList.add(Class.forName(stringType));
            }

            paramTypesArray = paramTypesList.toArray(new Class[paramTypesList.size()]);
            paramValues = get_paramValues();

            Method method = receiver.getMethod(get_methodName(), paramTypesArray);


            Serializer serializer = new Serializer();
            ArrayList<Object> realValues = new ArrayList<>();
            for (int i = 0; i < paramValues.length; i++) {
                realValues.add(serializer.decode(paramValues[i], paramTypesArray[i]));
            }

            //Object target = getTargetInstance(receiver);

            return (Results) method.invoke(null, realValues.toArray());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
