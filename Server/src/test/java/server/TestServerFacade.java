 package server;

 import static org.junit.Assert.* ;

 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;

 import java.util.Arrays;
 import java.util.List;

 import data.Command;
 import server.ServerFacade;


 public class TestServerFacade {

     private ServerFacade serverFacade;


     @Before
     public void setUp(){
         serverFacade = new ServerFacade();
         return;
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

 }

