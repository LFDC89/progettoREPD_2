package androidhive.info.materialdesign.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.fragments.CreditsFragment;
import androidhive.info.materialdesign.fragments.FragmentDrawer;
import androidhive.info.materialdesign.fragments.HomeFragment;
import androidhive.info.materialdesign.fragments.SearchFragment;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.fragments.UserInformationsFragment;


/** The MainActivity is the Fragments' container **/
public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener
{
    // tag for LOG message
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     // load the xml

        // toolbar and fragments stuff
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // retrieve user foods stored with SharedPreferences method (saved just the indexes)
        String user_info = DataPreferences.readPreference(getApplicationContext(), DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY);

        if (user_info.equals("no user info"))
        {
            // display the first navigation drawer view on app launch
            displayView(2);
        }
        else
        {
            // display the first navigation drawer view on app launch
            displayView(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_search)
        {
            SearchFragment fragment = new SearchFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position)
    {
            displayView(position);
    }

    private void displayView(int position)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position)
        {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new SearchFragment();
                title = getString(R.string.title_search_page);
                break;
            case 2:
                fragment = new UserInformationsFragment();
                title    = getString(R.string.title_user_info_page);
                break;
            case 3:
                fragment = new CreditsFragment();
                title = getString(R.string.title_credits_page);
                break;

            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }
    }


}

