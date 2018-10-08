package handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import data.Command;
import results.Results;
import data.Serializer;


/**
 * Represents the clear handler object instantiated when the /clear api is called
 */

public class ExcmdHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange)
    {
        try
        {
            //check for post... should be post
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                //*************** GET DATA FROM EXCHANGE ****************

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();


                //****************************************************
                //************** PERFORM SERVICE, ENCODE RESULTS ***************

                //in other words... get the command object and call execute.
                Serializer serializer = new Serializer();
                Command command = (Command)serializer.decodeFromStream(reqBody, Command.class);
                if (command.get_methodName() != "getCommands") {
                    System.out.println("Received (non-command) request");
                }
                Results results = command.execute();

                //************************************************
                //*************** SEND DATA BACK *****************

                //send response headers
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();

                //WRITE DATA TO RESPBODY
                serializer.encodeToStream(results, respBody);

                //SEND DATA
                respBody.close();

                //**************************************************
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }

    }
}
