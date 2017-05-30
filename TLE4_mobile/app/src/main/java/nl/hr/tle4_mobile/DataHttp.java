package nl.hr.tle4_mobile;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sonny on 5/29/2017.
 */

public class DataHttp extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        String result ="";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("ResourceVersion", "v3");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            int data = reader.read();

            while(data != -1){
                char current  = (char)data;
                result += current;
                data = reader.read();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        String results;

        try {
            JSONObject jsonObject = new JSONObject(result);
            String flight = jsonObject.getString("flights");

            JSONArray arr = new JSONArray(flight);

            for(int i = 0; i < arr.length(); i++){
                JSONObject jsonObject1 = arr.getJSONObject(i);

                String flightname = jsonObject1.getString("flightName");
                String landingTime = jsonObject1.getString("estimatedLandingTime");
                String estimatedTimeOnBelt = jsonObject1.getString("expectedTimeOnBelt");
                String test = jsonObject1.getString("checkinAllocations");

                // get baggage belt from an array and convert it into string
                JSONObject bc = jsonObject1.getJSONObject("baggageClaim");
                String bc1 = bc.getString("belts");
                JSONArray bc2 = new JSONArray(bc1);
                String bc3 = bc2.getString(0);

                Log.i("flightinfo", bc3);
                Log.i("flightinfo", estimatedTimeOnBelt);
                Log.i("flightinfo;", flightname);

                if(test == "null") {
                    Log.i("flightinfo", "test came back null");
                }else {
                    Log.i("flightinfo", test);
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
