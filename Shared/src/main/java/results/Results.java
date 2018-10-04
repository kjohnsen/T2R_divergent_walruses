package results;

import java.util.ArrayList;

import data.Command;

abstract public class Results {

    private Boolean isSuccess;
    private String errorMessage;
    ArrayList<Command> clientCommands = new ArrayList();

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
