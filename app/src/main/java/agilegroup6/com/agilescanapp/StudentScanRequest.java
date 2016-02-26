package agilegroup6.com.agilescanapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StudentScanRequest extends AppCompatActivity
{
    private TextView contentTextView;
    private TextView formatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scan_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        contentTextView = (TextView) findViewById(R.id.contentTextView);
        formatTextView = (TextView) findViewById(R.id.formatTextView);

        getIntentData();
    }

    // function to grab the data from the passed intent
    public void getIntentData()
    {
        // get the extras and place them into a bundle
        Bundle scanData = getIntent().getExtras();

        // if scan data isn't empty, then assign the data to strings for now
        if(scanData != null)
        {
            String content = scanData.getString("Content");
            contentTextView.setText(content);

            String format = scanData.getString("Format");
            formatTextView.setText(format);
        }
    }
}
