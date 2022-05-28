package uk.ac.shef.oak.jobserviceexample.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectNetwork {

    private static final String TAG = "Network";
    private static HttpURLConnection urlConnection = null;
    private static BufferedReader reader = null;
    private static String JSONString;

    public static String getBackendInfo() {
        try{

            Log.d(TAG,"Connecting .... ");
            Uri builtUri = Uri.parse("http://10.0.2.2:5000/getjobs")
                    .buildUpon()
                    .build();



            URL requestUrl = new URL(builtUri.toString());

            // Open the network connection.
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.d(TAG,"Connected ");

            // Get the InputStream and create a stringBuilder
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();


            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }
            JSONString = buffer.toString();
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader != null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d(TAG, "getting the json data");

            Log.d("Json", "JSON DATA : " + JSONString );

            return JSONString;
        }
    }