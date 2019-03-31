package ro.pub.cs.systems.eim.colocviu01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Colocviu02 extends AppCompatActivity {

    EditText superiorText;
    Button okButton;
    Button cancelButton;

    private GenericListener gt = new GenericListener();
    private class GenericListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.okbutton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelbutton:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu02);

        superiorText = (EditText) findViewById(R.id.editext1);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("numberOfClicks")) {
            int numberOfClicks = intent.getIntExtra("numberOfClicks", -1);
            superiorText.setText(String.valueOf(numberOfClicks));
        }

        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setOnClickListener(gt);
        cancelButton = (Button) findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(gt);


    }
}
