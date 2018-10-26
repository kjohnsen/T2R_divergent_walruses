package presenter;

import fragment.IChatView;

public class MockChatView implements IChatView {
    private Boolean sendEnabled = null;
    private Boolean shouldReload = null;
    private String errorMessage = null;

    public MockChatView() {
    }

    public Boolean getSendEnabled() {
        return sendEnabled;
    }

    public void setSendEnabled(Boolean sendEnabled) {
        this.sendEnabled = sendEnabled;
    }

    public Boolean getShouldReload() {
        return shouldReload;
    }

    public void setShouldReload(Boolean shouldReload) {
        this.shouldReload = shouldReload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void clearVariables() {
        errorMessage = null;
        shouldReload = null;
        sendEnabled = null;
    }

    @Override
    public void sendButtonSetEnabled(Boolean enabled) {
        sendEnabled = enabled;
    }

    @Override
    public void chatViewShouldReloadData() {
        shouldReload = true;
    }

    @Override
    public void showError(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
