package edu.nyit.evence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.nyit.evence.db.SessionManager;

/**
 * Created by Frank on 4/12/2015.
 */
public class CreateEvent_4 extends Activity implements RadioGroup.OnCheckedChangeListener {

    private Button btnPrevious;
    private Button btnFinish;
    private String op1;
    private RadioGroup radioSelect;
    private EditText txtArriving;
    private EditText txtDeparting;

    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_4);

        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }

        radioSelect = (RadioGroup) findViewById(R.id.radioSelect);
        radioSelect.setOnCheckedChangeListener(this);

        txtArriving = (EditText) findViewById(R.id.txtArriving);
        txtDeparting = (EditText) findViewById(R.id.txtDeparting);
        actv(false);

        btnFinish = (Button) findViewById(R.id.btnFinish);
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
            case R.id.radioDefault:
                op1 = "default";
                actv(false);
                break;

            case R.id.radioCustom:
                op1 = "custom";
                actv(true);
                break;
        }
    }

    private void actv(final boolean active) {
        txtArriving.setEnabled(active);
        txtDeparting.setEnabled(active);
        if (active) {
            txtDeparting.requestFocus();
            txtArriving.requestFocus();

        }
    }
}