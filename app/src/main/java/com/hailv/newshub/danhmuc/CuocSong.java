package com.hailv.newshub.danhmuc;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.hailv.newshub.CategoriesActivity;
import com.hailv.newshub.FavActivity;
import com.hailv.newshub.MainActivity;
import com.hailv.newshub.R;
import com.hailv.newshub.adapter.NewsAdapter;
import com.hailv.newshub.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CuocSong extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoc_song);
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

        new DownloadTask().execute("https://guu.vn/cat/guu-cuoc-song");
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
            ListView listView = findViewById(R.id.listView);
            NewsAdapter newsAdapter = new NewsAdapter(CuocSong.this,R.layout.item_main,news);
            listView.setAdapter(newsAdapter);
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
        getMenuInflater().inflate(R.menu.cuoc_song, menu);
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
            startActivity(new Intent(CuocSong.this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_categories) {
            startActivity(new Intent(CuocSong.this, CategoriesActivity.class));
            finish();
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(CuocSong.this, FavActivity.class));
            finish();
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(CuocSong.this, MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
