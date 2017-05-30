package nl.hr.tle4_mobile;

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
import java.util.ArrayList;

/**
 * Created by sonny on 5/29/2017.
 */

public class SchipholApi extends AsyncTask<String, Void, ArrayList<String>> {
    private WeakReference<FlightStatusActivity> flightActivity;

    public SchipholApi(FlightStatusActivity activity){
        flightActivity = new WeakReference<FlightStatusActivity>(activity);
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        ArrayList<String> result = new ArrayList<String>();
        result.add("");

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
                result.add( Integer.toString(current));
                data = reader.read();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {

        super.onPostExecute(result);
        JSONObject jsonObject = new JSONObject();
        try {
            for(String item: result){
                jsonObject += (JSONObject) item;
            };
            String flight = jsonObject.getString("flights");

            JSONArray arr = new JSONArray(flight);

            for(int i = 0; i < arr.length(); i++){
                JSONObject jsonObject1 = arr.getJSONObject(i);

                String flightname = jsonObject1.getString("flightName");
                String landingTime = jsonObject1.getString("estimatedLandingTime");
                String estimatedTimeOnBelt = jsonObject1.getString("expectedTimeOnBelt");
                String test = jsonObject1.getString("checkinAllocations");

                // get baggage belt from an array and convert it into string
                JSONObject baggageObject = jsonObject1.getJSONObject("baggageClaim");
                String baggageBelt = baggageObject.getString("belts");
                JSONArray baggageArray = new JSONArray(baggageBelt);
                String belt = baggageArray.getString(0);

                Log.i("flightinfo", belt);
                Log.i("flightinfo", estimatedTimeOnBelt);
                Log.i("flightinfo;", flightname);

                if(test == "null") {
                    Log.i("flightinfo", "test came back null");
                }else {
                    Log.i("flightinfo", test);
                }

                result.add("Flight name: " + flightname + "\r\n"
                        + "Baggage belt number: " + belt + "\r\n"
                        + "Estimated time on belt: " + estimatedTimeOnBelt);

                flightActivity.get().setInfo(estimatedTimeOnBelt);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
