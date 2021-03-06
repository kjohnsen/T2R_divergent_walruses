package results;

import java.io.Serializable;
import java.util.ArrayList;

import data.Command;

public class Results implements Serializable {

    private Boolean isSuccess;
    private String errorMessage;
    ArrayList<Command> clientCommands = new ArrayList<>();
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Results() {
        isSuccess = false;
    }

    public ArrayList<Command> getClientCommands() {
        return clientCommands;
    }

    public void setClientCommands(ArrayList<Command> clientCommands) {
        this.clientCommands = clientCommands;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
