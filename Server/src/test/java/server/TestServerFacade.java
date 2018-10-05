 package server;

 import static org.junit.Assert.* ;

 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;

 import java.util.Arrays;
 import java.util.List;

 import data.Command;
 import data.CommandManager;
 import model.ServerModel;
 import results.Results;
 import modelclasses.User;


 public class TestServerFacade {

     private ServerFacade serverFacade;

     @Before
     public void setUp(){
         serverFacade = new ServerFacade();
         return;
     }

     @Before
     public void enterUser() {
         User user = new User("user1", "pass");
         ServerModel.getInstance().getUsers().put("user1", user);
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
         Results results = serverFacade.loginUser("user1", "pass");
         assertTrue(results.getSuccess());
         assertNull(results.getErrorMessage());
         assertEquals(1, results.getClientCommands().size());
         assertNull(CommandManager.getInstance().getCommands("user1"));
     }

     @Test
     public void loginInvalidPassword() {
        Results results = serverFacade.loginUser("user1", "incorrectPassword");
        assertEquals("Password incorrect", results.getErrorMessage());
     }

     @Test
     public void loginNonexistentUser() {
         Results results = serverFacade.loginUser("notAUser", "password");
         assertEquals("Username doesn't exist", results.getErrorMessage());
     }

     @Test
     public void registerUserTest() {
         Results results = serverFacade.registerUser("newUser", "word");
         assertTrue(results.getSuccess());
         assertNull(results.getErrorMessage());
         assertEquals(1, results.getClientCommands().size());

         User u = ServerModel.getInstance().getUsers().get("newUser");
         assertEquals("word", u.getPassword());
     }

     @Test
     public void registerRepeatUsername() {
        Results results = serverFacade.registerUser("user1", "newPassword");
        assertFalse(results.getSuccess());
        assertEquals("Username already exists!", results.getErrorMessage());
     }

     @Test
     public void createGame() {

     }

     @Test
     public void joinGame() {

     }

 }

