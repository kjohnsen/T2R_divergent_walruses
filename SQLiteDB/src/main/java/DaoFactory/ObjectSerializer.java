package DaoFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;


public class ObjectSerializer {

    public static String serializeObject(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            Base64.Encoder encoder = Base64.getEncoder();
            return new String(encoder.encode(baos.toByteArray()));
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object deserializeObject(byte[] serializedObject) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] commandByteArray = decoder.decode(serializedObject);
            ByteArrayInputStream baip = new ByteArrayInputStream(commandByteArray);
            ObjectInputStream ois = new ObjectInputStream(baip);
            return ois.readObject();
        } catch (java.io.IOException | java.lang.ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
