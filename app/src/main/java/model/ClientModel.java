package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import modelclasses.User;

public class ClientModel implements PropertyChangeListener {
    private User currentUser;

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
