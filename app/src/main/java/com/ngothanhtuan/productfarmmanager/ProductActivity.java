package com.ngothanhtuan.productfarmmanager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ngothanhtuan.controls.CustomActivity;

public class ProductActivity extends CustomActivity {

    private static final int REQUEST_CODE = 2;

    int ID;

    AddPrFragment addPrFragment = new AddPrFragment();
    ListProductFragment listProductFragment = new ListProductFragment();
    DetailFragment detailFragment = new DetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getInt("ID");

        addLeft_menu();
        addControls();
        addEvent();

    }

    private void addControls() {
        Configuration config = getResources().getConfiguration();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.FrgList,listProductFragment);

        if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
            hideFragment();
        }else {
            showFragment();
        }
        transaction.commit();
    }

    private void addEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt("ID",ID);
        listProductFragment.setArguments(bundle);
        addPrFragment.setArguments(bundle);

    }

    private void addLeft_menu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //Show fragment
    private void showFragment() {
        View update = findViewById(R.id.FrgDetail);
        if (update.getVisibility() == View.GONE){
            update.setVisibility(View.VISIBLE);
        }
    }

    //Hide fragment
    private void hideFragment() {
        View update = findViewById(R.id.FrgDetail);
        if (update.getVisibility() == View.VISIBLE){
            update.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_type) {
            Intent intent = new Intent(this,TypeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ID);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE);
        } else if (id == R.id.nav_product) {

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ID);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ItemAdd:
                Configuration config = getResources().getConfiguration();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
                    transaction.replace(R.id.FrgList,addPrFragment);
                }else {
                    transaction.replace(R.id.FrgDetail,addPrFragment);
                }

                transaction.commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem add = menu.findItem(R.id.ItemAdd);
        add.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
}
