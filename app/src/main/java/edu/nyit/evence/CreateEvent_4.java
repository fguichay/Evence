package edu.nyit.evence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Created by Frank on 4/12/2015.
 */
public class CreateEvent_4 extends Activity implements RadioGroup.OnCheckedChangeListener {

    Button btnFinish;
    private String op1;
    private RadioGroup rg1;
    private EditText edit;
    private EditText edit2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_4);

        rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rg1.setOnCheckedChangeListener(this);

        edit = (EditText) findViewById(R.id.editText1);
        edit2 = (EditText) findViewById(R.id.editText2);
        actv(false);

        btnFinish = (Button) findViewById(R.id.button3);
        btnFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainEventsActivity.class);
                startActivity(intent);

            }

        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio0:
                op1 = "default";
                actv(false);
                break;

            case R.id.radio1:
                op1 = "custom";
                actv(true);
                break;
        }
    }

    private void actv(final boolean active) {
        edit.setEnabled(active);
        edit2.setEnabled(active);
        if (active) {
            edit2.requestFocus();
            edit.requestFocus();

        }
    }
}