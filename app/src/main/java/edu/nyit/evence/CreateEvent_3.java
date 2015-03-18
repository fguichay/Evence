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
import android.widget.Toast;

public class CreateEvent_3 extends Activity {

    Button btnFinish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3);

        btnFinish = (Button) findViewById(R.id.button3);
        btnFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainEventsActivity.class);
                startActivity(intent);

            }

        });


    }

}
