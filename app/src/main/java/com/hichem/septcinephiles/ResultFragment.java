package com.hichem.septcinephiles;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ResultFragment extends Fragment {

    private static ListView result;
    private List<Movie> movieList = new ArrayList<Movie>();
    private MovieListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.result_fragment,
                container, false);

        result = (ListView) view.findViewById(R.id.listr);
        adapter = new MovieListAdapter(getActivity(), movieList);
        result.setAdapter(adapter);


        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie entry= (Movie) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), MoviePage.class);
                intent.putExtra("titre", entry.getTitle());
                startActivity(intent);
            }
        });

        return view;
    }

    public void loadResultList(String text) {
        movieList.clear();
        String url = "http://www.omdbapi.com/?s=" + text;
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest (
                Request.Method.GET, url , null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray r = jsonObject.getJSONArray("Search");
                            for (int i = 0; i < r.length(); i++) {
                                Movie m = new Movie();
                                String year = r.getJSONObject(i).getString("Year");
                                year = year.substring(0, 4);
                                m.setYear(Integer.parseInt(year));
                                m.setThumbnailUrl(r.getJSONObject(i).getString("Poster"));
                                m.setTitle(r.getJSONObject(i).getString("Title"));
                                m.setGenre(new ArrayList<String>());
                                m.setRating(0.0);
                                movieList.add(m);
                            }

                            adapter.notifyDataSetChanged();

                            } catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );


        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}

