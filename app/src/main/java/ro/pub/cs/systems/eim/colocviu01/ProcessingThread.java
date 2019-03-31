package ro.pub.cs.systems.eim.colocviu01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    Context context;

    public boolean isRunning = true;

    private double arithmeticMean;
    private double geometricMean;

    public static final String[] types = {"arithmetic", "geometric"};

    Random r = new Random();

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d("[Processing Thread]", "Thread has started");
        while(isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[Processing Thread]", "Thread has stopped");
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(types[r.nextInt(types.length)]);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
