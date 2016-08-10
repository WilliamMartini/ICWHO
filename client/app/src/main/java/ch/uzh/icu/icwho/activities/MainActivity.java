package ch.uzh.icu.icwho.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import ch.uzh.icu.icwho.AppState;
import ch.uzh.icu.icwho.R;
import ch.uzh.icu.icwho.fragments.AboutFragment;
import ch.uzh.icu.icwho.fragments.EventsFragment;
import ch.uzh.icu.icwho.fragments.GebaeudeFragment;
import ch.uzh.icu.icwho.fragments.MensaFragment;
import ch.uzh.icu.icwho.fragments.NewsFragment;
import ch.uzh.icu.icwho.fragments.UeberUnsFragment;
import ch.uzh.icu.icwho.fragments.XKCDFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        i = (ImageView) findViewById(R.id.startImage);

        InputStream is = null;
        try {
            is = getAssets().open("background_cropped.png");
            Drawable d = Drawable.createFromStream(is, null);
            i.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (AppState.currentState == 0) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                //super.onBackPressed();
                drawer.openDrawer(GravityCompat.START);
            }
        } else if (AppState.currentState == 1) {
            setFragment(R.id.container_main, new EventsFragment());
            AppState.currentState = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        getSupportActionBar().setTitle(item.getTitle());
        i.setImageDrawable(null);

        AppState.currentState = 0;

        int id = item.getItemId();
        // set respective fragment
        if (id == R.id.nav_news) {
            setFragment(R.id.container_main, new NewsFragment());
        } else if (id == R.id.nav_events) {
            setFragment(R.id.container_main, new EventsFragment());
        } else if (id == R.id.nav_gebaeude) {
            setFragment(R.id.container_main, new GebaeudeFragment());
        } else if (id == R.id.nav_mensa) {
            setFragment(R.id.container_main, new MensaFragment());
        } else if (id == R.id.nav_xkcd) {
            setFragment(R.id.container_main, new XKCDFragment());
        } else if (id == R.id.nav_ueberuns) {
            setFragment(R.id.container_main, new UeberUnsFragment());
        } else if (id == R.id.nav_about) {
            setFragment(R.id.container_main, new AboutFragment());
        }

        // collapse navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // helper method: show "popup"
    Toast toast;
    public void toaster(String s){

        if(toast == null)
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        else
        {
            toast.setText(s);
        }
        toast.show();
    }

    // helper method: change fragment
    public void setFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
