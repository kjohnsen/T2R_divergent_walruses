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
                System.out.println("Received request");
                //*************** GET DATA FROM EXCHANGE ****************

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                //write request body to string
                Serializer serializer = new Serializer();
                String reqData = serializer.readString(reqBody);

                //****************************************************
                //************** PERFORM SERVICE, ENCODE RESULTS ***************

                //in other words... get the command object and call execute.
                Command command = (Command)serializer.decode(reqData, Command.class);
                Results results = command.execute();

                //changes result into json to send back.
                String respData = serializer.encode(results);

                //************************************************
                //*************** SEND DATA BACK *****************

                //send response headers
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();

                //WRITE DATA TO RESPBODY
                serializer.writeString(respData, respBody);

                //SEND DATA
                respBody.close();

                System.out.println("Response sent");
                //**************************************************
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }

    }
}
