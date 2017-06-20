package nl.hr.tle4_mobile;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class LoginRequest extends AsyncTask<String,Void,String> {
    private WeakReference<LoginActivity> activityRef;
    String loginURL = "https://brusque-millimeters.000webhostapp.com/login.php";

    public LoginRequest(LoginActivity context){
        this.activityRef = new WeakReference<LoginActivity>(context);
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        try {
            URL url = new URL(this.loginURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String postData = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufw.write(postData);
            bufw.flush();
            bufw.close();
            outputStream.close();

            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();

            con.disconnect();

            //result.replace("\"\"","");

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected void onPostExecute(String result){
        System.out.println(result);
        System.out.println("\"" + this.activityRef.get().getUsername() + "\"");
        this.activityRef.get().setLoginResults(result);
        if(result.equals("\"" + this.activityRef.get().getUsername() + "\"")){
            Intent in = new Intent(this.activityRef.get(), FlightActivity.class);
            this.activityRef.get().startActivity(in);
        }
    }

    @Override
    protected void onProgressUpdate(Void...values){
        super.onProgressUpdate(values);
    }
}