package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateEvent_3 extends Activity {

    Button btnFinish;
    ListView lv;
    EditText et;
    ArrayList<Model> modelList;
    CustomAdapter adapter;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3);

        lv = (ListView) findViewById(R.id.lv);
        et = (EditText) findViewById(R.id.et);

        modelList = new ArrayList<Model>();
        adapter = new CustomAdapter(getApplicationContext(), modelList);
        lv.setAdapter(adapter);

        btnFinish = (Button) findViewById(R.id.button3);
        btnFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CreateEvent_4.class);
                startActivity(intent);

            }

        });


    }

    public void addbtn(View v) {
        String name = et.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Plz enter Values",
                    Toast.LENGTH_SHORT).show();
        } else {
            Model md = new Model(name);
            modelList.add(md);
            adapter.notifyDataSetChanged();
            et.setText("");
        }

    }


}
