package com.example.arsen.menu;

import android.app.FragmentTransaction;
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
import android.widget.Toast;

import com.example.arsen.menu.Fragments.AddFragment;
import com.example.arsen.menu.Fragments.BuyFragment;
import com.example.arsen.menu.Fragments.ControlFragment;
import com.example.arsen.menu.Fragments.ExchangeFragment;
import com.example.arsen.menu.Fragments.SellFragment;
import com.example.arsen.menu.Fragments.SimSimFragment;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AddFragment fadd;
    BuyFragment fbuy;
    SellFragment fsell;
    ControlFragment fcontrol;
    ExchangeFragment fexchange;
    SimSimFragment fsimsim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fadd = new AddFragment();
        fbuy = new BuyFragment();
        fsell = new SellFragment();
        fcontrol = new ControlFragment();
        fexchange = new ExchangeFragment();
        fsimsim = new SimSimFragment();
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        ftrans.replace(R.id.container, fsimsim);//.addToBackStack(null);
        setTitle(R.string.app_name);
        ftrans.commit();

        Global_variables.CURRENT_STORAGE = DBHelper.Get_Current_Storage(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().popBackStack()){

            }
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
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
        Boolean is_some_storage = !Global_variables.CURRENT_STORAGE.equals(getString(R.string.no_any_storage));
            int id = item.getItemId();

            FragmentTransaction ftrans = getFragmentManager().beginTransaction();
            if (id == R.id.add_item) {
                if (is_some_storage)
                    ftrans.replace(R.id.container, fadd).addToBackStack(null);
                else
                    Toast.makeText(this, R.string.message_add_first_storage, Toast.LENGTH_LONG).show();
            } else if (id == R.id.sell_item) {
                if (is_some_storage)
                    ftrans.replace(R.id.container, fsell).addToBackStack(null);
                else
                    Toast.makeText(this, R.string.message_add_first_storage, Toast.LENGTH_LONG).show();
            } else if (id == R.id.buy_item) {
                if (is_some_storage)
                    ftrans.replace(R.id.container, fbuy).addToBackStack(null);
                else
                    Toast.makeText(this, R.string.message_add_first_storage, Toast.LENGTH_LONG).show();
            } else if (id == R.id.control_items) {
                if (is_some_storage)
                    ftrans.replace(R.id.container, fcontrol).addToBackStack(null);
                else
                    Toast.makeText(this, R.string.message_add_first_storage, Toast.LENGTH_LONG).show();
            } else if (id == R.id.exchange_items) {
                if (is_some_storage) {
                    int database_size = DBHelper.Get_database_size(this);
                    if (database_size > 1) {
                        ftrans.replace(R.id.container, fexchange).addToBackStack(null);
                    } else {
                        Toast.makeText(this, R.string.message_add_second_storage, Toast.LENGTH_LONG).show();
                        Intent ToAddingStorage = new Intent(this, AddStorage.class);
//                        ToAddingStorage.putExtra("From_exchange", true);
                        startActivity(ToAddingStorage);
                    }
                }
                else
                    Toast.makeText(this, R.string.message_add_first_storage, Toast.LENGTH_LONG).show();
            } else if (id == R.id.home_page) {
                ftrans.replace(R.id.container, fsimsim).addToBackStack(null);
            }
            ftrans.commit();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);//TODO WHAT IS IT
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
