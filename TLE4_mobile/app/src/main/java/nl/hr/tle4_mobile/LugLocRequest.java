package nl.hr.tle4_mobile;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LugLocRequest extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            //connecting to first item in urls from param
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while(data != -1){
                char current = (char)data;

                result += current;
                data = reader.read();
            }

            reader.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text;
        //get the objects out of results
        try {
            JSONObject jsonObject = new JSONObject(result);
            String info = jsonObject.getString("feeds");
            JSONArray arr = new JSONArray(info);

            for(int i = 0; i < arr.length(); i++) {
                JSONObject jsonPart = arr.getJSONObject(i);

                String time = jsonPart.getString("created_at");
                String[] timer = time.split("T");
                time = timer[1].replace("Z","");
                String UID = jsonPart.getString("field1");

                Constants.luggageArrival = time;
                Constants.luggageTag = UID;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
