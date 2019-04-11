package com.hailv.newshub;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hailv.newshub.danhmuc.CuocSong;
import com.hailv.newshub.danhmuc.GiaiTri;
import com.hailv.newshub.danhmuc.LamDep;
import com.hailv.newshub.danhmuc.NguoiNoiTieng;
import com.hailv.newshub.danhmuc.TamSu;
import com.hailv.newshub.danhmuc.ThoiTrang;

public class CategoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
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
        getMenuInflater().inflate(R.menu.categories, menu);
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
            CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, MainActivity.class));
            CategoriesActivity.this.finish();
        } else if (id == R.id.nav_categories) {
            CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, CategoriesActivity.class));
            CategoriesActivity.this.finish();
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(CategoriesActivity.this, FavActivity.class));
            finish();
        } else if (id == R.id.nav_logout) {
            CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, MainActivity.class));
            CategoriesActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToThoiTrang(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, ThoiTrang.class));
        CategoriesActivity.this.finish();
    }

    public void goToLamDep(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, LamDep.class));
        CategoriesActivity.this.finish();
    }

    public void goToCuocSong(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, CuocSong.class));
        CategoriesActivity.this.finish();
    }

    public void goToTamSu(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, TamSu.class));
        CategoriesActivity.this.finish();
    }

    public void goToNguoiNoiTieng(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, NguoiNoiTieng.class));
        CategoriesActivity.this.finish();
    }

    public void goToGiaiTri(View view) {
        CategoriesActivity.this.startActivity(new Intent(CategoriesActivity.this, GiaiTri.class));
        CategoriesActivity.this.finish();
    }
}
