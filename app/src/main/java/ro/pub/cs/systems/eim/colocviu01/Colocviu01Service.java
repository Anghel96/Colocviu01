package ro.pub.cs.systems.eim.colocviu01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu01Service extends Service {
    ProcessingThread pr;

    public Colocviu01Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = intent.getIntExtra("firstnum", -1);
        int secondNumber = intent.getIntExtra("secondnum", -1);
        pr = new ProcessingThread(this, firstNumber, secondNumber);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onDestroy() {
        pr.stopThread();
    }
}
