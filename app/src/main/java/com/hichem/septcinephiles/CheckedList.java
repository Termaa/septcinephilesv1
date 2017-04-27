package com.hichem.septcinephiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CheckedList extends AppCompatActivity {

    ListView cl;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_list);

        cl = (ListView) findViewById(R.id.listcl);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SingletonFile.getInstance().getInput("checked.txt" , this));

        cl.setAdapter(adapter);

        cl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), MoviePage.class);
                i.putExtra("titre", SingletonFile.getInstance().getInput("checked.txt" , view.getContext())[position]);
                startActivity(i);
            }
        });
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
