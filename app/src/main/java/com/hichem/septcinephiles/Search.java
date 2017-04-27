package com.hichem.septcinephiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Search extends AppCompatActivity implements SearchFragment.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onButtonClick(String text) {
        ResultFragment rf;
        rf = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        rf.loadResultList(text);

     }
}
