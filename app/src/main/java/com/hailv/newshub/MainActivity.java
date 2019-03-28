package com.hailv.newshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hailv.newshub.adapter.NewsAdapter;
import com.hailv.newshub.model.News;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ListView lvMain;
    public ArrayList<News> newsList;
    public NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addListview();
    }


    public void addListview() {
        lvMain = findViewById(R.id.lvMain);
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(MainActivity.this, R.layout.item_main, newsList);
        lvMain.setAdapter(newsAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
                Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsList.add(new News("Bai viet thu nghiem", "ASDFSADFASDFASDFASDFASDF", "http://i.imgur.com/DvpvklR.png", "http://us.cnn.com/videos/us/2019/03/27/jussie-smollett-lawyer-don-lemon-intv-ctn-vpx.cnn"));
        newsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));
            MainActivity.this.finish();
        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_logout) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
