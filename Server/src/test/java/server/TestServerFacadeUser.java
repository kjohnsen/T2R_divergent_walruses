 package server;

 import com.sun.corba.se.spi.activation.Server;
 import com.sun.corba.se.spi.activation.ServerManagerOperations;

 import static org.junit.Assert.* ;

 import org.junit.Before;
 import org.junit.Test;

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 import data.Command;
 import data.CommandManager;
 import model.ServerModel;
 import modelclasses.ChatMessage;
 import modelclasses.GameInfo;
 import modelclasses.GameName;
 import modelclasses.Player;
 import results.Results;
 import modelclasses.User;


 public class TestServerFacadeUser {


     @Before
     public void setUp(){

     }

     @Before
     public void enterUser() {
         User user = new User("anotheruser", "pass");
         ServerModel.getInstance().getUsers().put("anotheruser", user);
     }

     @Test
     public void test() {

         String username = "parker";
         String password = "asdf";

         List<Object> listParameters = Arrays.asList(new Object[] {username, password});
         Command testCommand = new Command("server.ServerFacade","loginUser", listParameters);

         testCommand.execute();

         try{
             Class<?> testClass = Class.forName("java.lang.String");
             System.out.print(testClass);
         } catch (ClassNotFoundException e) {

         }
     }

     @Test
     public void loginUserTest() {
         Results results = ServerFacade.getInstance().loginUser("anotheruser", "pass");
         assertTrue(results.getSuccess());
         assertNull(results.getErrorMessage());
         assertEquals(1, results.getClientCommands().size());
         assertEquals(0, CommandManager.getInstance().getCommands("anotheruser").size());
     }

     @Test
     public void loginInvalidPassword() {
        Results results = ServerFacade.getInstance().loginUser("anotheruser", "incorrectPassword");
        assertEquals("Password incorrect", results.getErrorMessage());
     }

     @Test
     public void loginNonexistentUser() {
         Results results = ServerFacade.getInstance().loginUser("notAUser", "password");
         assertEquals("Username doesn't exist", results.getErrorMessage());
     }

     @Test
     public void registerUserTest() {
         Results results = ServerFacade.getInstance().registerUser("newUser", "word");
         assertTrue(results.getSuccess());
         assertNull(results.getErrorMessage());
         assertEquals(1, results.getClientCommands().size());

         User u = ServerModel.getInstance().getUsers().get("newUser");
         assertEquals("word", u.getPassword());
     }

     @Test
     public void registerRepeatUsername() {
        Results results = ServerFacade.getInstance().registerUser("anotheruser", "newPassword");
        assertFalse(results.getSuccess());
        assertEquals("Username already exists!", results.getErrorMessage());
     }

     @Test
     public void addChatMessage() {
         GameName gameName = new GameName("game");
         Player player = new Player("User");
         ArrayList<Player> players = new ArrayList<>();
         players.add(player);

         GameInfo gameInfo = new GameInfo(gameName, players, 2);
         Map<GameName, GameInfo> games = new HashMap<>();
         games.put(gameName, gameInfo);
         ServerModel.getInstance().setGames(games);

         ChatMessage chatMessage = new ChatMessage("User", "New Chat");
         Results results = ServerFacade.getInstance().sendChatMessage(chatMessage, gameName);

         assertTrue(results.getSuccess());
         assertTrue(ServerModel.getInstance().getChatMessages().size() == 1);
         assertTrue(results.getClientCommands().size() == 1);
     }

 }

