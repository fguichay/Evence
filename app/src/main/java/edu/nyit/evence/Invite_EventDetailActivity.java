package edu.nyit.evence;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by Frank on 5/6/2015.
 */

// This activity is only used in single pane mode.
public class Invite_EventDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_activity_event_detail);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
//          Create the fragment, set its args, add it to the detail container
            Invite_EventDetailFragment fragment = new Invite_EventDetailFragment();

            Bundle b = getIntent().getBundleExtra(MainEventsActivity.EVENT_BUNDLE);
            fragment.setArguments(b);

            getFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit();
        }

    }

    //  Returns to the list activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}