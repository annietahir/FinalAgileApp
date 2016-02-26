package agilegroup6.com.agilescanapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import agilegroup6.com.agilescanapp.models.JSONProcessor;
import agilegroup6.com.agilescanapp.models.UserModel;

public class MainActivity extends AppCompatActivity
{
    private Button dualLogin;
    private EditText loginDetails;
    private EditText passwordDetails;

    // STANDARD BLANK ACTIVITY CODE
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupComponents();
    }

    // set up the buttons and their on click functions
    public void setupComponents()
    {
        // link up the buttons to the UI
        dualLogin = (Button) findViewById(R.id.dualLoginButton);
        //Get the two edit texts
        loginDetails = (EditText) findViewById(R.id.loginDetails);
        passwordDetails = (EditText) findViewById(R.id.passwordDetails);;
        // create button events
        dualLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //CODE FOR ASYNC HTTP POST ADAPTED FROM
                //stackoverflow.com/questions/7860538 by Mitsuaki Ishimoto
                //So lets get the JSON response
                JSONProcessor processor = new JSONProcessor();
                processor.setmListener(new JSONProcessor.Listener() {
                    UserModel um = new UserModel();

                    @Override
                    public void onResult(String response) {
                        System.out.println(response);
                        if (response.equals("staff")) {
                            System.out.println(response);
                            //Set the user info
                            um.setId(loginDetails.getText().toString());
                            um.setAccesslevel(response);
                        } else if (response.equals("student")) {
                            System.out.println(response);
                            //Set the user info
                            um.setId(loginDetails.getText().toString());
                            um.setAccesslevel(response);
                        }
                        //Check the users access level and then go to the necessary area after that
                        if(um.getAccesslevel() == "staff"){
                            Intent staffLoginIntent = new Intent(MainActivity.this, StaffMenu.class);
                            startActivity(staffLoginIntent);
                        } else if(um.getAccesslevel() == "student"){
                            //Go to student activity
                            Intent studentLoginIntent = new Intent(MainActivity.this, StudentScanRequest.class);
                            startActivity(studentLoginIntent);
                        }
                    }
                });
                //Execute the code
                processor.execute(loginDetails.getText().toString(),passwordDetails.getText().toString());


            }
        });


        /*
        // check for when the button has been pressed by the user
        studentLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(v.getId() == R.id.studentLoginButton)
                {
                    // creates instance of zxing integration class
                    IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);

                    // starts the scan
                    scanIntegrator.initiateScan();

                    // NOTE
                    //When you call the initiateScan method, you can choose
                    // to pass a collection of the barcode types you want to
                    // scan. By default, the method will scan for all supported
                    // types. These include UPC-A, UPC-E, EAN-8, EAN-13,
                    // QR Code, RSS-14, RSS Expanded, Data Matrix,
                    // Aztec, PDF 417, Codabar, ITF, Codes 39, 93, and 128.
                }
            }
        });
        */
    }


    // retrieves the scan results and processes them
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        // retrieve the results below
        // this parses the result into an instance of the ZXing intent result class
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        // if the results are not empty, then place the data into a placeholder for now.
        // Then pass the data onto the StudentScanRequest class.
        if (scanResult != null)
        {
            // retrieve the content from the scn as a string
            String scanContent = scanResult.getContents();

            //retrieves the format name, as a string
            String scanFormat = scanResult.getFormatName();

            Intent studentScan = new Intent(MainActivity.this, StudentScanRequest.class);
            studentScan.putExtra("Content", scanContent);
            studentScan.putExtra("Format", scanFormat);
            startActivity(studentScan);
        }
        else
        {
            Toast message = Toast.makeText(getApplicationContext(), "No data was scanned", Toast.LENGTH_LONG);

            message.show();
        }
    }

    // STANDARD BLANK ACTIVITY CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // STANDARD BLANK ACTIVITY CODE
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
