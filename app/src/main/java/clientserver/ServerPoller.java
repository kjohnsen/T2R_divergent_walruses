package clientserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.security.Provider;
import java.util.ArrayList;

import data.Command;
import model.ClientModel;

public class ServerPoller extends Service{
    private static final ServerPoller ourInstance = new ServerPoller();
    private Handler handler = null;
    private static final long DEFAULT_POLL_INTERVAL = 2 * 1000; //2 seconds

    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            getCommands();
            handler.postDelayed(runnableService, DEFAULT_POLL_INTERVAL);
        }
    };

    private ServerPoller() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static ServerPoller getInstance() {
        return ourInstance;
    }

    private synchronized void getCommands() {
        //Need access to the client model...
        //ArrayList<Command> commands = ServerProxy.getInstance().getCommands()
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        handler.post(runnableService);
        return START_STICKY;
    }
}