package com.hailv.newshub;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hailv.newshub.Login.LoginActivity;
import com.hailv.newshub.adapter.NewsAdapter;
import com.hailv.newshub.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ListView lvMain;
    public ArrayList<News> newsList;
    public NewsAdapter newsAdapter;
    private TextView tvEmail;

    public static final String MAIN_URL = "https://guu.vn/";

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvEmail = findViewById(R.id.tvEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        tvEmail.setText("Welcome "+ user.getEmail());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new DownloadTask().execute(MAIN_URL);
    }

    private class DownloadTask extends AsyncTask<String, Void, ArrayList<News>> {

        private static final String TAG = "DownloadTask";

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            Document document = null;
            ArrayList<News> listArticle = new ArrayList<>();
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div#latest-news > div.row > div.col-md-6");
                    for (Element element : sub) {
                        News news = new News();
                        Element titleSubject = element.getElementsByTag("h3").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();
                        Element descrip = element.getElementsByTag("h4").first();
                        //Parse to model
                        if (titleSubject != null) {
                            String title = titleSubject.text();
                            news.setTitle(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("src");
                            news.setThumnail(src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            news.setUrl(link);
                        }
                        if (descrip != null) {
                            String des = descrip.text();
                            news.setDecription(des);
                        }
                        //Add to list
                        listArticle.add(news);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return listArticle;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);
            //Setup data recyclerView
            lvMain = findViewById(R.id.lvMain);
            newsAdapter = new NewsAdapter(MainActivity.this,R.layout.item_main,news);
            lvMain.setAdapter(newsAdapter);
            newsAdapter.notifyDataSetChanged();
        }
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
            MainActivity.this.startActivity(new Intent(MainActivity.this, CategoriesActivity.class));
            MainActivity.this.finish();
        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();
            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
