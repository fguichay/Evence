package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class CreateEvent_1 extends Activity {

    Button btnNextPage;
    Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        btnNextPage = (Button) findViewById(R.id.button1);

        btnNextPage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, CreateEvent_2.class);
                startActivity(intent);

            }

        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainEventsActivity.class);
                startActivity(i);
            }
        });

    }


}