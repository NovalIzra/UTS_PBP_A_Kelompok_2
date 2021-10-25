package com.travel.travelskuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.travelskuy.ui.ListFragment;
import com.travel.travelskuy.ui.SettingFragment;
import com.travel.travelskuy.ui.TicketFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity  implements  BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragment(new TicketFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view_customer);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_customer, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_ticket:
                fragment = new TicketFragment();
                break;
            case R.id.navigation_listticket:
                fragment = new ListFragment();
                break;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                break;
        }
        return loadFragment(fragment);

    }


}