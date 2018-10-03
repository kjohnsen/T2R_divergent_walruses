package clientserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.security.Provider;
import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import model.ClientModel;
import model.UIFacade;

public class ServerPoller extends Service{
    private static final ServerPoller ourInstance = new ServerPoller();
    private static Handler handler = null;
    private static final long DEFAULT_POLL_INTERVAL = 2 * 1000; //2 seconds
    private static IServer serverProxy = ServerProxy.getInstance();

    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            getCommands();

            //Post this service again in 2 seconds...
            handler.postDelayed(runnableService, DEFAULT_POLL_INTERVAL);
        }
    };

    private ServerPoller() {}

    public static IServer getServerProxy() {
        return serverProxy;
    }

    public static void setServerProxy(IServer serverProxy) {
        ServerPoller.serverProxy = serverProxy;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static ServerPoller getInstance() {
        return ourInstance;
    }

    private synchronized void getCommands() {
        //Where are authtoken and clientID?
        ArrayList<Command> commands = serverProxy.getCommands(UIFacade.getInstance().getAuthToken(), UIFacade.getInstance().getAuthToken());
        for(int i = 0; i < commands.size(); ++i) {
            commands.get(i).execute();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        handler.post(runnableService);
        return START_STICKY;
    }
}