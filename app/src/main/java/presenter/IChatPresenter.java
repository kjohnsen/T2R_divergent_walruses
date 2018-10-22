package presenter;

import java.util.List;

import modelclasses.ChatMessage;

public interface IChatPresenter {
    void chatMessageEditTextDidChange(String text);
    void sendMessageButtonWasPressed(String chatMessage);
    List<ChatMessage> getChatMessages();
}
