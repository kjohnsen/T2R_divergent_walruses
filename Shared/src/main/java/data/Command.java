package data;

import com.google.gson.internal.LinkedTreeMap;

import modelclasses.User;
import results.Results;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements Serializable {

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

    //another simplified constructor
    public Command(String className, String methodName, List<Object> parameters){
        ArrayList<String> paramTypes = new ArrayList<>();
        ArrayList<Object> _parameters = new ArrayList<>();

        for(Object object : parameters) {
            paramTypes.add(object.getClass().getName());
            _parameters.add(object);
        }

        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes.toArray(new String[0]);
        _paramValues = _parameters.toArray();
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

            Class<?>[] paramTypesArray;
            Object[] paramValues;

            //turn param types from string to Class
            ArrayList<Class<?>> paramTypesList = new ArrayList<>();
            for(String stringType : get_paramTypes()) {
                paramTypesList.add(Class.forName(stringType));
            }

            paramTypesArray = paramTypesList.toArray(new Class[paramTypesList.size()]);
            paramValues = get_paramValues();

            Method method = receiver.getMethod(get_methodName(), paramTypesArray);

            Object target = getTargetInstance(receiver);

            return (Results) method.invoke(target, paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (get_className() != null ? !get_className().equals(command.get_className()) : command.get_className() != null)
            return false;
        if (get_methodName() != null ? !get_methodName().equals(command.get_methodName()) : command.get_methodName() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(get_paramTypes(), command.get_paramTypes())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(get_paramValues(), command.get_paramValues());
    }

    @Override
    public int hashCode() {
        int result = get_className() != null ? get_className().hashCode() : 0;
        result = 31 * result + (get_methodName() != null ? get_methodName().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(get_paramTypes());
        result = 31 * result + Arrays.hashCode(get_paramValues());
        return result;
    }
}