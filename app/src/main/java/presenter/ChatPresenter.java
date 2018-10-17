package presenter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fragment.IChatView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.ChatMessage;

public class ChatPresenter implements IChatPresenter, Observer {
    private IChatView chatView;

    public ChatPresenter(IChatView chatView) {
        this.chatView = chatView;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void chatMessageEditTextDidChange(String text) {
        chatView.sendButtonSetEnabled(!text.isEmpty());
    }

    @Override
    public void sendMessageButtonWasPressed(String chatMessage) {
        ChatMessage message = new ChatMessage(UIFacade.getInstance().getUsername(), chatMessage);
        String errorMessage = UIFacade.getInstance().sendChatMessage(message);
        if(errorMessage != null) {
            chatView.showError(errorMessage);
        }
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return UIFacade.getInstance().getChatMessages();
    }

    @Override
    public void update(Observable observable, Object o) {
        chatView.chatViewShouldReloadData();
    }
}
