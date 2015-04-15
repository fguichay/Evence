package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import edu.nyit.evence.custom.CustomAdapter;
import edu.nyit.evence.custom.Model;
import edu.nyit.evence.db.SessionManager;

public class CreateEvent_3 extends Activity {

    private Button btnPrevious;
    private Button btnNext;
    private ListView lvGuestlist;
    private EditText txtEmail;
    private ArrayList<Model> myGuestlist;
    private CustomAdapter adapter;

    private SessionManager session;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3);

        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }

        lvGuestlist = (ListView) findViewById(R.id.lvGuestlist);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        myGuestlist = new ArrayList<Model>();
        adapter = new CustomAdapter(getApplicationContext(), myGuestlist);
        lvGuestlist.setAdapter(adapter);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CreateEvent_4.class);
                startActivity(intent);

            }
        });
    }

    public void addbtn(View v) {
        String name = txtEmail.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Plz enter Values",
                    Toast.LENGTH_SHORT).show();
        } else {
            Model md = new Model(name);
            myGuestlist.add(md);
            adapter.notifyDataSetChanged();
            txtEmail.setText("");
        }
    }
}
