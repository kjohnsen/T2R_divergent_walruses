package presenter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fragment.IChatView;
import model.ClientModel;
import model.IUIFacade;
import model.UIFacade;
import modelclasses.ChatMessage;

public class ChatPresenter implements IChatPresenter, Observer {
    private IChatView chatView;
    private IUIFacade uiFacade;

    public IChatView getChatView() {
        return chatView;
    }

    public void setChatView(IChatView chatView) {
        this.chatView = chatView;
    }

    public IUIFacade getUiFacade() {
        return uiFacade;
    }

    public void setUiFacade(IUIFacade uiFacade) {
        this.uiFacade = uiFacade;
    }

    public ChatPresenter(IChatView chatView) {
        this.chatView = chatView;
        this.uiFacade = UIFacade.getInstance();
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void chatMessageEditTextDidChange(String text) {
        chatView.sendButtonSetEnabled(!text.isEmpty());
    }

    @Override
    public String sendMessageButtonWasPressed(String chatMessage) {
        ChatMessage message = new ChatMessage(UIFacade.getInstance().getUsername(), chatMessage);
        return uiFacade.sendChatMessage(message);
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
