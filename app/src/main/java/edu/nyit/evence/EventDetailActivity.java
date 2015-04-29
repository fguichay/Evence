package edu.nyit.evence;

/**
 * Created by Frank on 4/28/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

// This activity is only used in single pane mode.
public class EventDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
//          Create the fragment, set its args, add it to the detail container
            EventDetailFragment fragment = new EventDetailFragment();

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