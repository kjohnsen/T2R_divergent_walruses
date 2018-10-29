package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import model.ClientModel;
import model.MockServerProxy;
import model.UIFacade;
import modelclasses.ChatMessage;

import static org.junit.Assert.*;

public class ChatPresenterTest {
    private static ChatPresenter chatPresenter;
    private static MockChatView chatView = new MockChatView();
    private static MockUIFacade uiFacade = new MockUIFacade();
    private static MockServerProxy mockServerProxy = new MockServerProxy();

    @BeforeClass
    public static void setup() {
        chatPresenter = new ChatPresenter(chatView);
        chatPresenter.setUiFacade(uiFacade);
        uiFacade.setServerProxy(mockServerProxy);
    }

    @Before
    public void before() {
        chatView.clearVariables();
        chatPresenter.setChatView(chatView);
    }

    @Test
    public void enableSendButton() {
        String notEmpty = "Not Empty";
        String empty = "";

        chatPresenter.chatMessageEditTextDidChange(notEmpty);
        assertTrue(chatView.getSendEnabled());

        chatPresenter.chatMessageEditTextDidChange(empty);
        assertTrue(!chatView.getSendEnabled());
    }

    @Test
    public void dataChanged() {
        List<ChatMessage> chatMessages = ClientModel.getInstance().getChatMessages();
        chatPresenter.update(null, chatMessages);

        assertTrue(chatView.getShouldReload());
    }

    @Test
    public void sendMessage() {
        String success = chatPresenter.sendMessageButtonWasPressed("Test Message");
        String fail = chatPresenter.sendMessageButtonWasPressed("Error");
        String nullString = chatPresenter.sendMessageButtonWasPressed("Null");

        assertNull(success);
        assertTrue(fail.equals("Error Message"));
        assertTrue(nullString.equals("Cannot reach server"));
    }

}
