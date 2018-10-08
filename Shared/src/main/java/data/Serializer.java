package data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Parker on 3/4/18.
 */


public class Serializer {

    public Serializer(){}

    public String encode(Object objectToEncode) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(objectToEncode);
            return new String(bos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Object decode(String serializedObject, Class toJsonClass)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject.getBytes());
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

//    public Object decodeInnerClass(LinkedTreeMap l, Class toJsonClass) {
//        JsonObject obj = gson.toJsonTree(l).getAsJsonObject();
//        return decode(gson.toJson(obj), toJsonClass);
//    }
//
//    public Object decodeFile(String fileName, Class toJsonClass)
//    {
//        try
//        {
//            File file = new File(fileName);
//            FileReader fileReader = new FileReader(file);
//            return gson.fromJson(fileReader, toJsonClass);
//        }
//        catch(FileNotFoundException e)
//        {
//            System.out.print(e.getMessage());
//        }
//        catch(Exception e)
//        {
//            System.out.print(e.getMessage());
//        }
//        return null;
//    }

    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}