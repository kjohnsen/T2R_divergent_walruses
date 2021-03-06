package clientserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import data.Command;
import data.Serializer;
import model.UIFacade;
import results.Results;

public class ClientCommunicator {
    private Serializer serializer = new Serializer();
    private String serverHost = "localhost";
    private String serverPort = "8080";
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
            connection.setConnectTimeout(1000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //Send the auth token if there is one...
            if(UIFacade.getInstance().getAuthToken() != null) {
                connection.addRequestProperty("Authorization", UIFacade.getInstance().getAuthToken());
            }

            //Add the command to the request body...
            connection.connect();
            OutputStream requestBody = connection.getOutputStream();
            serializer.encodeToStream(command, requestBody);
            requestBody.close();

            //Get back the results. There should always be results unless there was a serious error...
            InputStream responseBody = connection.getInputStream();
            Results results = (Results)serializer.decodeFromStream(responseBody, Results.class);

            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}