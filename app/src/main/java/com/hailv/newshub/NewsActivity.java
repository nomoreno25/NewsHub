package com.hailv.newshub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.hailv.newshub.model.News;
import com.hailv.newshub.model.PrefManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public WebView webView;
    private PrefManager prefManager;
    private News news;
    private SharedPreferences shared;
    private ArrayList<String> arrTitle;
    private ArrayList<String> arrDesc;
    private ArrayList<String> arrThumbnail;
    private ArrayList<String> arrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        prefManager = new PrefManager(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            tvEmail.setText(email);
            Log.e("email", email);
        }

        news = (News) getIntent().getSerializableExtra("linkbaiviet");
        String tieude = news.getTitle();
        String mota = news.getDecription();
        String hinhanh = news.getThumnail();
        String linkbaiviet = "https://guu.vn" + news.getUrl();

        webView = findViewById(R.id.webView);
        loadUrl(linkbaiviet);

        shared = getSharedPreferences("listnews", MODE_PRIVATE);
        // add values for your ArrayList any where...
        if (arrTitle == null) {
            arrTitle = new ArrayList<>();
        }
        if (arrDesc == null) {
            arrDesc = new ArrayList<>();
        }
        if (arrThumbnail == null) {
            arrThumbnail = new ArrayList<>();
        }
        if (arrUrl == null) {
            arrUrl = new ArrayList<>();
        }
        arrTitle.add(tieude);
        arrDesc.add(mota);
        arrThumbnail.add(hinhanh);
        arrUrl.add(linkbaiviet);
    }

    private void packagesharedPreferences() {
        SharedPreferences.Editor editor = shared.edit();
        Set<String> setTitle = new HashSet<String>();
        Set<String> setDesc = new HashSet<String>();
        Set<String> setThumbnail = new HashSet<String>();
        Set<String> setUrl = new HashSet<String>();
        setTitle.addAll(arrTitle);
        setDesc.addAll(arrDesc);
        setThumbnail.addAll(arrThumbnail);
        setUrl.addAll(arrUrl);
        editor.putStringSet("TITLE_LIST", setTitle);
        editor.putStringSet("DESC_LIST", setDesc);
        editor.putStringSet("THUMBNAIL_LIST", setThumbnail);
        editor.putStringSet("URL_LIST", setUrl);
        editor.apply();
    }

    public void loadUrl(String url) {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
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
        getMenuInflater().inflate(R.menu.news, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fav) {
            packagesharedPreferences();
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
            NewsActivity.this.startActivity(new Intent(NewsActivity.this, MainActivity.class));
            NewsActivity.this.finish();
        } else if (id == R.id.nav_categories) {
            NewsActivity.this.startActivity(new Intent(NewsActivity.this, CategoriesActivity.class));
            NewsActivity.this.finish();
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(NewsActivity.this, FavActivity.class));
            finish();
        } else if (id == R.id.nav_logout) {
            NewsActivity.this.startActivity(new Intent(NewsActivity.this, MainActivity.class));
            NewsActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
