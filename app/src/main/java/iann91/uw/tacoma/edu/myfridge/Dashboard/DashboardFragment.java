package iann91.uw.tacoma.edu.myfridge.Dashboard;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import iann91.uw.tacoma.edu.myfridge.GroceryListFragment;
import iann91.uw.tacoma.edu.myfridge.Inventory.InventoryFragment;
import iann91.uw.tacoma.edu.myfridge.PlanWeekFragment;
import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.MyRecipesFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.SearchRecipeFragment;
import iann91.uw.tacoma.edu.myfridge.ScannerFragment;


/**
 * Dashboard fragment for displaying dashboard buttons for navigation.
 * Main home screen of the application.
 * @author iann91 Munkh92
 * @version 1.0
 */
public class DashboardFragment extends Fragment {

    private OnDashboardFragmentInteractionListener mListener;
    private SharedPreferences mSharedPreferences;


    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Initializes the necessary fields and views for the fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE );

        View view = inflater.inflate(R.layout.fragment_dashboard, container,
                false);

        final ImageButton inventoryButton = (ImageButton) view.findViewById(R.id.inventory_button);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Fragment inventoryFragment = new InventoryFragment();

                if(!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
                    Bundle b = new Bundle();
                    int myID = getArguments().getInt("id");
                    Log.i("MY ID",""+ myID);
                    b.putInt("id", myID);
                    inventoryFragment.setArguments(b);
                }



                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(inventoryFragment);
            }
        });

        final ImageButton searchRecipesButton = (ImageButton) view.findViewById(R.id.search_button);
        searchRecipesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment searchRecipesFragment = new SearchRecipeFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(searchRecipesFragment);
            }
        });

        final ImageButton groceryListButton = (ImageButton) view.findViewById(R.id.grocery_button);
        groceryListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment groceryListFragment = new GroceryListFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(groceryListFragment);
            }
        });

        final ImageButton myRecipesButton = (ImageButton) view.findViewById(R.id.recipes_button);
        myRecipesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment myRecipeFragment = new MyRecipesFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(myRecipeFragment);
            }
        });

        final ImageButton scannerButton = (ImageButton) view.findViewById(R.id.scanner_button);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment scannerFragment = new ScannerFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(scannerFragment);
            }
        });

        final ImageButton planWeekButton = (ImageButton) view.findViewById(R.id.plan_week_button);
        planWeekButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment planWeekFragment = new PlanWeekFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(planWeekFragment);
            }
        });




        // Inflate the layout for this fragment
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        return view;
    }

    /**
     * Sets listener to dashboard fragment listener when attached.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashboardFragmentInteractionListener) {
            mListener = (OnDashboardFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDashboardFragmentInteractionListener {
        void onDashboardFragmentInteraction(Fragment fragment);
    }

}
