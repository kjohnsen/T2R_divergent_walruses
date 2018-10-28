package presenter;

import java.util.List;

import modelclasses.ChatMessage;

public interface IChatPresenter {
    void chatMessageEditTextDidChange(String text);
    String sendMessageButtonWasPressed(String chatMessage);
    List<ChatMessage> getChatMessages();
}
