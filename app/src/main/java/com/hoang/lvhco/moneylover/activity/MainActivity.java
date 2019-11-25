package com.hoang.lvhco.moneylover.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hoang.lvhco.moneylover.GioiThieuFragment;
import com.hoang.lvhco.moneylover.KhoanChiFragment;
import com.hoang.lvhco.moneylover.KhoanThuFramgent;
import com.hoang.lvhco.moneylover.R;
import com.hoang.lvhco.moneylover.ThongKeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Khoản thu");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_khoanthu:
                        selectedFragment = new KhoanThuFramgent();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container, selectedFragment).commit();
                        setTitle("Khoản thu");
                        return true;
                    case R.id.navigation_khoanchi:
                        setTitle("Khoản chi");
                        selectedFragment = new KhoanChiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container, selectedFragment).commit();
                        return true;
                    case R.id.navigation_thongke:
                        setTitle("Thống kê");
                        selectedFragment = new ThongKeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container, selectedFragment).commit();

                        return true;

                }
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container, new KhoanThuFramgent()).commit();

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
//        int id = item.getItemId();
    switch (item.getItemId()){
        case R.id.KhoaUngDung:
            Toast.makeText(this, "Khóa ứng dụng", Toast.LENGTH_SHORT).show();
            break;
        case R.id.ThongTin:

        Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
        startActivity(intent);
            break;
        case R.id.PhienBan:
            Toast.makeText(this, "Phiên bản 1.0.1", Toast.LENGTH_SHORT).show();
            break;
    }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemKhoanThu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container,
                        new KhoanThuFramgent()).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_khoanthu);
                break;
            case R.id.itemKhoanChi:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container,
                        new KhoanChiFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_khoanchi);
                break;
            case R.id.itemThongKe:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container,
                        new ThongKeFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_thongke);
                break;
            case R.id.itemGioiThieu:
                Intent intent = new Intent(MainActivity.this,GioiThieuActivity.class);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container,
//                        new GioiThieuFragment()).commit();

                break;
            case R.id.itemThoat:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bạn có muốn thoát không");
                builder.setIcon(R.drawable.iconthoat);
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
