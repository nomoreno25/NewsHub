package com.hailv.newshub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hailv.newshub.Login.LoginActivity;
import com.hailv.newshub.adapter.NewsAdapter;
import com.hailv.newshub.model.News;
import com.hailv.newshub.model.PrefManager;

import java.util.ArrayList;
import java.util.Set;

public class FavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private ListView lvFav;
    private ArrayList<News> newsList;
    private NewsAdapter newsAdapter;
    private ArrayList<News> news;
    private PrefManager prefManager;
    private SharedPreferences shared;
    private ArrayList<String> arrTitle;
    private ArrayList<String> arrDesc;
    private ArrayList<String> arrThumbnail;
    private ArrayList<String> arrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            tvEmail.setText(email);
            Log.e("email",email);
        }
        shared = getSharedPreferences("listnews", MODE_PRIVATE);
        if(arrTitle==null){
            arrTitle = new ArrayList<>();
        }
        if (arrDesc==null){
            arrDesc = new ArrayList<>();
        }
        if (arrThumbnail==null){
            arrThumbnail = new ArrayList<>();
        }
        if (arrUrl==null){
            arrUrl = new ArrayList<>();
        }
        retriveSharedValue();
        news = new ArrayList<>();
        int i = 0;
        for (i = 0;i<arrTitle.size();i++){
            news.add(new News(arrTitle.get(i),arrUrl.get(i),arrThumbnail.get(i),arrDesc.get(i)));
        }
        loadListView();
    }

    private void retriveSharedValue() {
        Set<String> setTitle = shared.getStringSet("TITLE_LIST", null);
        Set<String> setDesc = shared.getStringSet("DESC_LIST", null);
        Set<String> setThumbnail = shared.getStringSet("THUMBNAIL_LIST", null);
        Set<String> setUrl = shared.getStringSet("URL_LIST", null);
        arrTitle.addAll(setTitle);
        arrDesc.addAll(setDesc);
        arrThumbnail.addAll(setThumbnail);
        arrUrl.addAll(setUrl);
    }

    private void loadListView(){
        lvFav = findViewById(R.id.lvFav);
        newsAdapter = new NewsAdapter(FavActivity.this,R.layout.item_main,news);
        lvFav.setAdapter(newsAdapter);
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
        getMenuInflater().inflate(R.menu.fav, menu);
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
            startActivity(new Intent(FavActivity.this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_categories) {
            startActivity(new Intent(FavActivity.this, CategoriesActivity.class));
            finish();
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(FavActivity.this, FavActivity.class));
            finish();
        } else if (id == R.id.nav_logout) {
            firebaseAuth.getInstance().signOut();
            startActivity(new Intent(FavActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
