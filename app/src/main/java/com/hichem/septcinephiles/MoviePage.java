package com.hichem.septcinephiles;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;

public class MoviePage extends AppCompatActivity {

    // We use this to get more information about the movie, such as the list of actors and description etC.

    private static final String url = "http://www.omdbapi.com/?t=";
    private ArrayList<String> actorsList = new ArrayList<String>();
    private ListView actors;
    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        TextView titre = (TextView) findViewById(R.id.title);
        TextView genre = (TextView) findViewById(R.id.genres);
        final TextView description = (TextView) findViewById(R.id.description);
        final TextView note = (TextView) findViewById(R.id.note);
        TextView annee = (TextView) findViewById(R.id.annee);
        final NetworkImageView poster = (NetworkImageView) findViewById(R.id.poster);

        actors = (ListView) findViewById(R.id.actors);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, actorsList);
        actors.setAdapter(adapter);



        titre.setText(getIntent().getExtras().getString("titre"));
        genre.setText(getIntent().getExtras().getStringArrayList("genres").toString());
        annee.setText(new Integer(getIntent().getExtras().getInt("annee")).toString());



        JsonObjectRequest jsObjRequest = new JsonObjectRequest (
                Request.Method.GET, url + titre.getText() , null ,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        description.setText("Plot : " + jsonObject.getString("Plot"));
                        note.setText("Note : " + jsonObject.getString("imdbRating"));
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
                    description.setText("ERREUR RESPONSE");
                }
            }
        );


        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}
