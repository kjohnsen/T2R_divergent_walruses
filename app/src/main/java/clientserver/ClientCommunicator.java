package clientserver;

import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import data.Command;
import data.Serializer;
import model.ClientModel;
import results.LoggedInResults;
import results.Results;

public class ClientCommunicator {
    private Serializer serializer = new Serializer();
    private String serverHost = "localhost";
    private String serverPort = "8080";
    private String authToken = null;
    private static final ClientCommunicator ourInstance = new ClientCommunicator();

    private ClientCommunicator() {}

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public static ClientCommunicator getInstance() {
        return ourInstance;
    }

    public Results send(Command command) {
        try {
            //Make the URL...
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/excmd");

            //Get the connection
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //Send the auth token if there is one...
            if(authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            //Add the command to the request body...
            connection.connect();
            String serializedCommand = serializer.encode(command);
            OutputStream requestBody = connection.getOutputStream();
            serializer.writeString(serializedCommand, requestBody);
            requestBody.close();

            //Get back the results. There should always be results unless there was a serious error...
            InputStream responseBody = connection.getInputStream();
            String serializedResults = serializer.readString(responseBody);
            Results results = (Results)serializer.decode(serializedResults, Results.class);

            //Do we need this here or will the command facade add it?
            /*
            if(results instanceof LoggedInResults) {
                LoggedInResults loggedInResults = (LoggedInResults)results;
                if(loggedInResults.getSuccess()) {
                    authToken = loggedInResults.getAuthToken();
                }
            }
            */

            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}