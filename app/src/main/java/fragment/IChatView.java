package fragment;

public interface IChatView {
    void sendButtonSetEnabled(Boolean enabled);
    void chatViewShouldReloadData();
    void showError(String errorMessage);
}
