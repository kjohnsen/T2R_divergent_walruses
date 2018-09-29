package results;

import data.Command;

abstract public class Results {

    private Boolean isSuccess;
    Command[] clientCommands;
    private String errorMessage;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Command[] getClientCommands() {
        return clientCommands;
    }

    public void setClientCommands(Command[] clientCommands) {
        this.clientCommands = clientCommands;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
