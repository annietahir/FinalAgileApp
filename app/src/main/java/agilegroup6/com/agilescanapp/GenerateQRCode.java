package agilegroup6.com.agilescanapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GenerateQRCode extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

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

        generateCode();
    }

    public void generateCode()
    {
        Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
        intent.putExtra("ENCODE_FORMAT", "QR_CODE");
        intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
        intent.putExtra("ENCODE_DATA", "Class - Agile Programming");
        // intent.putExtra("ENCODE_DATA2", "Lecturer - Mark Snaith");
        //intent.putExtra("ENCODE_DATA3", "Time - 2:15pm");

        try
        {
            startActivityForResult(intent,0);
        } catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.zxing.client.android")));
        }
    }
}
