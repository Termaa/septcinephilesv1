package com.hichem.septcinephiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MoviePage extends AppCompatActivity {

    // We use this to get more information about the movie, such as the list of actors and description etC.

    private static final String url = "http://www.omdbapi.com/?t=";
    FileOutputStream fOut;
    private ArrayList<String> actorsList = new ArrayList<String>();
    private ListView actors;
    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_movie_page);

        final TextView titre = (TextView) findViewById(R.id.title);
        final TextView genre = (TextView) findViewById(R.id.genres);
        final TextView description = (TextView) findViewById(R.id.description);
        final TextView note = (TextView) findViewById(R.id.note);
        final TextView annee = (TextView) findViewById(R.id.annee);
        final NetworkImageView poster = (NetworkImageView) findViewById(R.id.poster);

        ImageButton watchlist = (ImageButton) findViewById(R.id.watchlist);

        ImageButton checked = (ImageButton) findViewById(R.id.checked);

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // access file and add it to watchlist

                for(String s : SingletonFile.getInstance().getInput("watchlist.txt", v.getContext()))
                    if (s.equals(titre.getText().toString())) {
                        Toast toast = Toast.makeText(v.getContext(), "already in watchlist",  Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                SingletonFile.getInstance().getOuput("watchlist.txt", titre.getText().toString() + ", ", v.getContext());
                Toast toast = Toast.makeText(v.getContext(), "added to watchlist",  Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // acces file and add it to checked
                for(String s : SingletonFile.getInstance().getInput("checked.txt", v.getContext()))
                    if (s.equals(titre.getText().toString())) {
                        Toast toast = Toast.makeText(v.getContext(), "already in checked list",  Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                SingletonFile.getInstance().getOuput("checked.txt", titre.getText().toString() + ", ", v.getContext());
                Toast toast = Toast.makeText(v.getContext(), "added as checked",  Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        actors = (ListView) findViewById(R.id.actors);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, actorsList);
        actors.setAdapter(adapter);

        titre.setText(getIntent().getExtras().getString("titre"));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest (
                Request.Method.GET, url + titre.getText() , null ,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        annee.setText(jsonObject.getString("Year"));
                        genre.setText(jsonObject.getString("Genre"));
                        description.setText(jsonObject.getString("Plot"));
                        note.setText(jsonObject.getString("imdbRating"));
                        poster.setImageUrl( jsonObject.getString("Poster"), AppController.getInstance().getImageLoader());
                        for (String str : jsonObject.getString("Actors").split(", "))
                            actorsList.add(str);

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    description.setText(volleyError.getMessage());
                }
            }
        );


        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mymovies:
                Intent intent = new Intent(this, CheckedList.class);
                startActivity(intent);
                return true;
            case R.id.watchlist:
                Intent intent2 = new Intent(this, WatchList.class);
                startActivity(intent2);
                return true;
            case R.id.search :
                Intent intent3 = new Intent(this, Search.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
