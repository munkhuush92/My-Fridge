package iann91.uw.tacoma.edu.myfridge;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private OnDashboardFragmentInteractionListener mListener;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container,
                false);

        final Button inventoryButton = (Button) view.findViewById(R.id.inventory_button);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment inventoryFragment = new InventoryFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(inventoryFragment);
            }
        });

        final Button searchRecipesButton = (Button) view.findViewById(R.id.search_button);
        searchRecipesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment searchRecipesFragment = new SearchRecipesFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(searchRecipesFragment);
            }
        });

        final Button groceryListButton = (Button) view.findViewById(R.id.grocery_button);
        groceryListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment groceryListFragment = new GroceryListFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(groceryListFragment);
            }
        });

        final Button myRecipesButton = (Button) view.findViewById(R.id.recipes_button);
        myRecipesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment myRecipeFragment = new MyRecipesFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(myRecipeFragment);
            }
        });

        final Button scannerButton = (Button) view.findViewById(R.id.scanner_button);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment scannerFragment = new ScannerFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(scannerFragment);
            }
        });

        final Button planWeekButton = (Button) view.findViewById(R.id.plan_week_button);
        planWeekButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment planWeekFragment = new PlanWeekFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(planWeekFragment);
            }
        });

        final Button calendarButton = (Button) view.findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment calendarFragment = new CalendarFragment();
                mListener = (OnDashboardFragmentInteractionListener)getActivity();
                mListener.onDashboardFragmentInteraction(calendarFragment);
            }
        });
        // Inflate the layout for this fragment
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        return view;
    }

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
        // TODO: Update argument type and name
        void onDashboardFragmentInteraction(Fragment fragment);
    }

}
