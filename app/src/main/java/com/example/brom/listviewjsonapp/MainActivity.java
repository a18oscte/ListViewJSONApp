package com.example.brom.listviewjsonapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


// Create a new class, Mountain, that can hold your JSON data

// Create a ListView as in "Assignment 2 - Toast and ListView"

// Retrieve data from Internet service using AsyncTask and the included networking code

// Parse the retrieved JSON and update the ListView adapter

// Implement a "refresh" functionality using Android's menu_main system


public class MainActivity extends AppCompatActivity {
    public ArrayList<Mountain> list=new ArrayList<Mountain>();

    public static final String EXTRA_MESSAGE = "MESSAGE";
    public static final String EXTRA_MESSAGE2 = "MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "MESSAGE4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchData().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh){
            new FetchData().execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }

        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);

            final ArrayList<Mountain> mountainArr = new ArrayList();

            class lista {
                public String name;
                public String lo;
                public String heigth;

                public lista(String name,String lo, String heigth) {
                    this.name = name;
                    this.lo = lo;
                    this.heigth = heigth;
                }
            }

            class UsersAdapter extends ArrayAdapter<lista> {
                public UsersAdapter(Context context, ArrayList<lista> users) {
                    super(context, 0, users);
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    // Get the data item for this position
                    lista user = getItem(position);
                    // Check if an existing view is being reused, otherwise inflate the view
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_textview, parent, false);
                    }
                    // Lookup view for data population
                    TextView tvn = (TextView) convertView.findViewById(R.id.list_item_textview);
                    TextView tvl = (TextView) convertView.findViewById(R.id.list_item_textview2);
                    TextView tvh = (TextView) convertView.findViewById(R.id.list_item_textview3);
                    // Populate the data into the template view using the data object
                    tvn.setText(user.name);
                    tvl.setText(user.lo);
                    tvh.setText(user.heigth);
                    // Return the completed view to render on screen
                    return convertView;
                }
            }

            // Construct the data source
            ArrayList<lista> arrayOfUsers = new ArrayList<lista>();
// Create the adapter to convert the array to views
            UsersAdapter adapter = new UsersAdapter(getApplicationContext(), arrayOfUsers);
// Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.my_listview);
            listView.setAdapter(adapter);




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String message = mountainArr.get(position).info();
                    String Title = mountainArr.get(position).namn();
                    String url = mountainArr.get(position).img();
                    String u = mountainArr.get(position).ur();
                    launchSecondActivity(view, message, Title, url, u);
                }
            });
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.


            try {

                JSONArray a = new JSONArray(o);
                for(int i = 0; i < a.length(); i++){
                    JSONObject json1 = (JSONObject) a.get(i);

                    JSONArray arr = new JSONArray("["+json1.getString("auxdata")+ "]");
                    JSONObject img = (JSONObject) arr.get(0);

                    Mountain m = new Mountain(json1.getString("name"),json1.getString("location"), json1.getInt("size"), img.getString("img"),img.getString("url") );
                    mountainArr.add(m);

                    lista newUser = new lista(mountainArr.get(i).namn(), mountainArr.get(i).loc(), mountainArr.get(i).hei());
                    adapter.add(newUser);

                }

            }catch (JSONException e) {
                Log.e("brom", "E:" + e.getMessage());
            }

        }




    }

    public void launchSecondActivity(View view, String n, String t, String u, String ur) {
        Intent intent = new Intent(this, MountainDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, n);
        intent.putExtra(EXTRA_MESSAGE2, t);
        intent.putExtra(EXTRA_MESSAGE3, u);
        intent.putExtra(EXTRA_MESSAGE4, ur);
        startActivity(intent);




    }


}

