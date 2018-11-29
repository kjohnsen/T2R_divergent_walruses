package clientserver;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.security.Provider;
import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import model.ClientModel;
import model.UIFacade;
import results.Results;

public class ServerPoller extends Service {
    private static Handler handler = null;
    private static final long DEFAULT_POLL_INTERVAL = 1 * 1000; //2 seconds
    private IServer serverProxy = ServerProxy.getInstance();

    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            //Start the async task...
            new GetCommandsTask().execute();

            //Post this service again in 2 seconds...
            handler.postDelayed(runnableService, DEFAULT_POLL_INTERVAL);
        }
    };

    public IServer getServerProxy() {
        return serverProxy;
    }

    public void setServerProxy(IServer serverProxy) {
        this.serverProxy = serverProxy;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Start the service...
        handler = new Handler();
        handler.post(runnableService);
        /* I think it needs to be not sticky because otherwise, it keeps trying to run even after the
        app is closed */
        return START_NOT_STICKY;
    }

    private class GetCommandsTask extends AsyncTask<Void, Void, ArrayList<Command>> {

        @Override
        protected ArrayList<Command> doInBackground(Void... voids) {
            //Send the request to the server...
            Results results = serverProxy.getCommands(UIFacade.getInstance().getAuthToken());
            if (results != null) {
                ArrayList<Command> commands = results.getClientCommands();
                if (commands != null) {
                    return commands;
                }
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Command> commands) {
            //Execute any commands that we get back...
            for (int i = 0; i < commands.size(); ++i) {
                commands.get(i).execute();
            }
        }
    }
}