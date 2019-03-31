package ro.pub.cs.systems.eim.colocviu01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Colocviu01MainActivity extends AppCompatActivity {
    private EditText leftText;
    private EditText rightText;
    private Button left;
    private Button right;
    private Button intentButton;

    public static final String[] types = {"arithmetic", "geometric"};

    private IntentFilter intentFilter = new IntentFilter();

    private int serviceStatus = 0;

    private ButtonListener bt = new ButtonListener();
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int leftNumberofClicks = Integer.valueOf(leftText.getText().toString());
            int rightNumberofClicks = Integer.valueOf(rightText.getText().toString());

            switch(v.getId()) {
                case R.id.press_button:
                    Intent intent = new Intent(getApplicationContext(), Colocviu02.class);
                    int numberOfClicks = Integer.parseInt(leftText.getText().toString()) +
                            Integer.parseInt(rightText.getText().toString());
                    intent.putExtra("numberOfClicks", numberOfClicks);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.incButton1:
                    int a = Integer.parseInt(leftText.getText().toString());
                    a += 1;
                    leftText.setText(String.valueOf(a));
                    break;

                case R.id.incButton2:
                    int b = Integer.parseInt(rightText.getText().toString());
                    b += 1;
                    rightText.setText(String.valueOf(b));
                    break;
            }
            if (leftNumberofClicks + rightNumberofClicks > 7 && serviceStatus == 0) {
                Intent intent = new Intent(getApplicationContext(), Colocviu01Service.class);
                intent.putExtra("firstnum", leftNumberofClicks);
                intent.putExtra("secondnum", rightNumberofClicks);
                getApplicationContext().startActivity(intent);
                serviceStatus = 1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colocviu_test01_main);

        leftText = (EditText)findViewById(R.id.increment1);
        rightText = (EditText)findViewById(R.id.increment2);
        left = (Button)findViewById(R.id.incButton1);
        right = (Button)findViewById(R.id.incButton2);


        right.setOnClickListener(bt);
        left.setOnClickListener(bt);


        if (savedInstanceState == null) {
            Log.d("this", "onCreate() method was invoked without a previous state");
        } else {
            if (savedInstanceState.containsKey("leftCount")) {
                leftText.setText(savedInstanceState.getString("leftCount"));
            }

            if (savedInstanceState.containsKey("rightCount")) {
                rightText.setText(savedInstanceState.getString("rightCount"));
            }
        }

        intentButton = (Button)findViewById(R.id.press_button);
        intentButton.setOnClickListener(bt);

        for (int index = 0; index < types.length; index++) {
            intentFilter.addAction(types[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("leftCount", leftText.getText().toString());
        savedInstanceState.putString("rightCount", rightText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("colocviu01", "restore instance");
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("leftCount")) {
            leftText.setText(savedInstanceState.getString("leftCount"));
        }

        if (savedInstanceState.containsKey("rightCount")) {
            rightText.setText(savedInstanceState.getString("rightCount"));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent i = new Intent(this, Colocviu01Service.class);
        stopService(i);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }
}
