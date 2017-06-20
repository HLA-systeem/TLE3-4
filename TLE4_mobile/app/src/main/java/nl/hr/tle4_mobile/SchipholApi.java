package nl.hr.tle4_mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sonny on 5/29/2017.
 */

public class SchipholApi extends AsyncTask<String, Void, String> {
    private WeakReference<FlightActivity> activityRef;

    public SchipholApi(FlightActivity activity){
        this.activityRef= new WeakReference<FlightActivity>(activity);
    }

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
//                String landingTime = jsonObject1.getString("estimatedLandingTime");
                String estimatedTimeOnBelt = jsonObject1.getString("expectedTimeOnBelt");
                String test = jsonObject1.getString("checkinAllocations");

                // get baggage belt from an array and convert it into string
                JSONObject baggageObject = jsonObject1.getJSONObject("baggageClaim");
                String baggageBelt = baggageObject.getString("belts");
                JSONArray baggageArray = new JSONArray(baggageBelt);
                String belt = baggageArray.getString(0);

//                Log.i("flightinfo", belt);
//                Log.i("flightinfo", estimatedTimeOnBelt);
//                Log.i("flightinfo;", flightname);
//
//                if(test == "null") {
//                    Log.i("flightinfo", "test came back null");
//                }else {
//                    Log.i("flightinfo", test);
//                }

                results = "Flight name: " + flightname + "\r\n"
                        + "Baggage belt: " + belt + "\r\n"
                        + "Estimated time on belt: " + estimatedTimeOnBelt;

                Constants.flightInfo = results;

                this.activityRef.get().showFlightInfo();
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
