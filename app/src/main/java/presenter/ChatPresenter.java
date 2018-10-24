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
    public String sendMessageButtonWasPressed(String chatMessage) {
        ChatMessage message = new ChatMessage(UIFacade.getInstance().getUsername(), chatMessage);
        return UIFacade.getInstance().sendChatMessage(message);
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return UIFacade.getInstance().getChatMessages();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o == ClientModel.getInstance().getChatMessages()) {
            chatView.chatViewShouldReloadData();
        }
    }
}
