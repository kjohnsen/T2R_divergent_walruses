 package server;

 import static org.junit.Assert.* ;

 import org.junit.Before;
 import org.junit.Test;

 import java.util.Arrays;
 import java.util.List;

 import data.Command;
 import data.CommandManager;
 import model.ServerModel;
 import results.Results;
 import modelclasses.User;


 public class TestServerFacadeUser {


     @Before
     public void setUp(){

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
         Results results = ServerFacade.getInstance().loginUser("user1", "pass");
         assertTrue(results.getSuccess());
         assertNull(results.getErrorMessage());
         assertEquals(1, results.getClientCommands().size());
         assertEquals(0, CommandManager.getInstance().getCommands("user1").size());
     }

     @Test
     public void loginInvalidPassword() {
        Results results = ServerFacade.getInstance().loginUser("user1", "incorrectPassword");
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
        Results results = ServerFacade.getInstance().registerUser("user1", "newPassword");
        assertFalse(results.getSuccess());
        assertEquals("Username already exists!", results.getErrorMessage());
     }

 }

