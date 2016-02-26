package agilegroup6.com.agilescanapp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Liam on 26/02/2016.
 */
public class HTTPPosterUnitTest {

    @Test
    public void testMakeLoginPost()throws Exception{

        String stringurl = "https://zeno.computing.dundee.ac.uk/2015-agile/team6/api/dual_login.php";
        //URL Object
        URL url = new URL(stringurl);

        //Create HTTPSurlConnection object
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        //Set the request method
        connection.setRequestMethod("POST");
        //Build the parameter string needed
        String postdata = "dualLogin=Submit&logindetail=3&password=test";
        //Send post the post request
        connection.setDoOutput(true);
        //Create the output stream
        DataOutputStream outstream = new DataOutputStream(connection.getOutputStream());
        //Write to the output stream
        outstream.writeBytes(postdata);
        //Flush the  output stream
        outstream.flush();
        //And then close it..
        outstream.close();
        //New buffered reader object that reads the response
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //String that we will use to read the reply line by line
        String lineReader;
        //String buffer that we use to buffer the response
        StringBuffer response = new StringBuffer();
        //While each line isn't null
        while((lineReader = br.readLine()) != null){
            response.append(lineReader);
        }
        //Close the buffered reader
        br.close();

        //String expected response
        String expectedresponse = "{\"status\":\"student_success\"}";

        //Return the response
        assertEquals(response.toString(),expectedresponse);
    }

}
