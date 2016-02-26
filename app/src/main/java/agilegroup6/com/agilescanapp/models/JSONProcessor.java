package agilegroup6.com.agilescanapp.models;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousCloseException;
import java.util.List;

/**
 * Created by Liam on 26/02/2016.
 *
 * CODE FOR ASYNC HTTP ADAPTED FROM
 * stackoverflow.com/uestions/7860538
 * BY Mitsuaki Ishimoto
 *
 */
public class JSONProcessor extends AsyncTask<String,String,String> {

    public interface Listener{
        void onResult(String result);
    }

    public Listener mListener;

    public void setmListener(Listener listener){
        mListener = listener;
    }

    public String getJSONForLogin(String user, String password) throws Exception {
        //Create an object of HTTPPoster
        HTTPPoster poster = new HTTPPoster();
        //Set the paraemeters in this object
        URL url = null;
        try {
            url = new URL("https://zeno.computing.dundee.ac.uk/2015-agile/team6/api/dual_login.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        poster.setUrl(url);
        poster.setLogin(user);
        poster.setPassword(password);
        //Connect to the PHP API
        String jsonResponse = poster.makeLoginPost();
        //String with the parsed json
        String parsedjson = jsonParse(jsonResponse);
        //If the user sucessfully logged in as a staff member
        if(parsedjson.equals("staff_success")) {
            //Set the user details
           return" staff";
        } else if(parsedjson.equals("student_success")){
            return "student";
        } else {
            System.out.println("it doesnt work" + parsedjson);
            //So it obviously equals false or just didn't work, return false
            return "failed";
        }
    }

    public String jsonParse(String json){
        JSONObject parser = null;
        //G
        try {
            parser = new JSONObject(json);
            String status = parser.getString("status");
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected String doInBackground(String... params) {
        //Try and get the result from the JSON for Login
        try {
            String result = getJSONForLogin(params[0],params[1]);
            //Return the response if possible
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //Return that the user could not login
            return "failed";
        }
    }

    @Override
    protected void onPostExecute(String result){
        //THIS CODE IS ADAPTED FROM
        //STACKOVERFLOW.COM/questions/7860538/android-http-post/asynctask
        if(mListener != null){
            mListener.onResult(result);
        }
    }

}
