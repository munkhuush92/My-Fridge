package iann91.uw.tacoma.edu.myfridge.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.URL;

import iann91.uw.tacoma.edu.myfridge.Authenticate.LoginActivity;
import iann91.uw.tacoma.edu.myfridge.CalendarFragment;
import iann91.uw.tacoma.edu.myfridge.GroceryListFragment;
import iann91.uw.tacoma.edu.myfridge.Inventory.AddItemFragment;
import iann91.uw.tacoma.edu.myfridge.Inventory.InventoryFragment;
import iann91.uw.tacoma.edu.myfridge.Inventory.ItemDetailFragment;
import iann91.uw.tacoma.edu.myfridge.Inventory.ItemFragment;
import iann91.uw.tacoma.edu.myfridge.PlanWeekFragment;
import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.MyDetailedRecipeFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.MyRecipesFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.RecipeDetailFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.RecipeFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.SearchRecipeFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;
import iann91.uw.tacoma.edu.myfridge.ScannerFragment;
import iann91.uw.tacoma.edu.myfridge.item.Item;

/**
 * Dashboard activity that holds all of the fragments for the application.
 * @author iann91 Munkh92
 * @version 1.0
 */
public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener,
        AddItemFragment.ItemAddDatabaseListener,
        ItemDetailFragment.ItemAddDatabaseListener,
        RecipeFragment.OnRecipeFragmentInteractionListener,
        ItemDetailFragment.ItemDeleteLocallyListener,
        InventoryFragment.ItemAddLocallyListener,
        InventoryFragment.SwapInventoryFragListener,
        SearchRecipeFragment.OnSearchFragmentInteractionListener
        ,MyRecipesFragment.OnSavedRecipeListFragmentInteractionListener
        ,MyDetailedRecipeFragment.GenerateRecipeListener

{

    private Map<String, ArrayList<Item>> mySortedItems;
    private ArrayList<Item> myItems;
    private static final String[] mCategories = {"Dairy", "Grains", "Vegetables", "Meat", "Fruit"};
    private String mLastSelectedCategory;
    protected DrawerLayout mDrawer;

    private String mGrocList;
    private boolean mGrocListIsFilled;


    /**
     * Initializes fields and sets up the view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myItems = new ArrayList<Item>();
        mySortedItems = new HashMap<String, ArrayList<Item>>();
        for(int i = 0; i < mCategories.length; i++) {
            mySortedItems.put(mCategories[i], new ArrayList<Item>());
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemFragment addItemFragment = new AddItemFragment();
                Bundle b = new Bundle();
                b.putString("Category", mLastSelectedCategory);
                addItemFragment.setArguments(b);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_dashboard, addItemFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        RecipeFragment recipeFragment = RecipeFragment.newInstance("hello");

        if (findViewById(R.id.content_dashboard) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            DashboardFragment dashboardFragment = new DashboardFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            dashboardFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_dashboard, dashboardFragment).commit();
        }



    }

    /**
     * Handles what happens to the navigation drawer when back is pressed on the device.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            SharedPreferences mSharedPref = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                    , Context.MODE_PRIVATE);

            if (!mSharedPref.getBoolean(getString(R.string.LOGGEDIN), false)) {

            }
            super.onBackPressed();
        }
    }

    public String setCategoryInventory(String category) {
        return category;
    }

    /**
     * Creates options menu on navbar.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    /**
     * Handles what happens when settings is selected on action bar.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_logout){
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE );
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            Log.i("LOGGIN OUT ", "MIKE");
            return true;
        }
        return false;

    }

    /**
     * Switches to necessary fragment when navigation item is selected.
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.inventory_dashboard) {
            fragment = new InventoryFragment();
        } else if (id == R.id.recipe_dashboard) {
            fragment = new SearchRecipeFragment();
        } else if (id == R.id.grocery_dashboard) {
            fragment = new GroceryListFragment();
        }
        else if (id == R.id.my_recipe_dash) {
            fragment = new MyRecipesFragment();
        }
        else if (id == R.id.scanner_dashboard) {
            fragment = new ScannerFragment();
        } else if (id == R.id.plan_week_dashboard) {
            fragment = new PlanWeekFragment();
        } else if (id == R.id.calendar_dashboard) {
            fragment = new CalendarFragment();
        } else if (id == R.id.logout_dash) {

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_dashboard, fragment).addToBackStack(null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Swaps out current fragment with the fragment passed in.
     * @param fragment to change to.
     */
    @Override
    public void onDashboardFragmentInteraction(Fragment fragment) {
        // Capture the course fragment from the activity layout
        if(fragment instanceof GroceryListFragment){
            Bundle bundle = new Bundle();
            bundle.putBoolean("List filled", mGrocListIsFilled);
            bundle.putString("Grocery List", mGrocList);
            fragment.setArguments(bundle);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_dashboard, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ;
    }

    /**
     * Swaps to item fragment along with category name
     * @param fragment to change to.
     */
    @Override
    public void swapToItemFragment(Fragment fragment, String category) {
        // Capture the course fragment from the activity layout
        mLastSelectedCategory = category;
        Bundle bundle = new Bundle();
        bundle.putString("Category", category);
        bundle.putSerializable("Items", mySortedItems.get(category));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_dashboard, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ;
    }


    /**
     * Creates item detail fragment and swaps it for current fragment when item is selected.
     * @param item
     */
    @Override
    public void onListFragmentInteraction(Item item) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ItemDetailFragment.COURSE_ITEM_SELECTED, item);
        itemDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_dashboard, itemDetailFragment)
                .addToBackStack(null)
                .commit();


    }

    /**
     * Creats an AddItem async task and executes it.
     * @param url
     */
    @Override
    public void addItemDatabase(String url) {
        AddItemTask task = new AddItemTask();
        task.execute(new String[]{url.toString()});

// Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onRecipeFragmentInteraction(RecipeContent item) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(RecipeDetailFragment.RECIPE_ITEM_SELECTED, item);
        recipeDetailFragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_dashboard, recipeDetailFragment)
                .addToBackStack(null)
                .commit();

    }



    @Override
    public void onSearchRecipeFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSavedListFragmentInteraction(RecipeContent item) {
        MyDetailedRecipeFragment mySavedRecipes = new MyDetailedRecipeFragment(item);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_dashboard, mySavedRecipes)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void deleteItem(String itemName, String itemType) {
        ArrayList<Item> temp = mySortedItems.get(itemType);
        for(int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getmItemName().equals(itemName)) {
                Log.i("FOUND ITEM TO DELETE", temp.get(i).getmItemName());
                Log.i("SIZE OF TEMP BEFORE", ""+ temp.size());
                temp.remove(i);
                Log.i("Size of temp after", "" + temp.size());
                mySortedItems.put(itemType, temp);

            }
        }
        ArrayList<Item> print;
        for(String item: mySortedItems.keySet()) {
            Log.i("Array TYPES", item);

            print = mySortedItems.get(item);
            for(int j = 0; j < print.size(); j++) {
                Log.i("Array items", print.get(j).getmItemName());
            }
        }
    }

    @Override
    public Map<String, ArrayList<Item>> addDownloadedItems(ArrayList<Item> theItems) {
        boolean duplicateFound = false;
        ArrayList<Item> tempList;
        for(int i = 0; i < theItems.size(); i++) {
            if(mySortedItems.containsKey(theItems.get(i).getmItemType())) {
                tempList = mySortedItems.get(theItems.get(i).getmItemType());
                for (int j = 0; j < tempList.size(); j++) {
                    if (tempList.get(j).getmItemName().equals(theItems.get(i).getmItemName())) {
                        duplicateFound = true;
                    }
                }
                if (!duplicateFound) {
                    tempList.add(theItems.get(i));
                    mySortedItems.put(theItems.get(i).getmItemType(), tempList);
                }
            }
        }
        Log.i("Here sorted items ", "" + mySortedItems.size());
        return mySortedItems;
    }

    @Override
    public void sendListIngredients(String listOfIngredients) {
        mGrocList = listOfIngredients;

    }

    @Override
    public void setChanged(boolean isAdded) {
        mGrocListIsFilled = isAdded;
    }

    private class AddItemTask extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add item, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Log.i("HERE", result);
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
