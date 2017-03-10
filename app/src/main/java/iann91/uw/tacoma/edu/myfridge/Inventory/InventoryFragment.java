package iann91.uw.tacoma.edu.myfridge.Inventory;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import iann91.uw.tacoma.edu.myfridge.R;


/**
 * Fragment to display the users inventory.
 * @author iann91 Munkh92
 */
public class InventoryFragment extends Fragment{

    private SwapInventoryFragListener mListener;

    public InventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Initializes necessary fields and view to display fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inventory, container,
                false);

        final Button dairyButton = (Button) view.findViewById(R.id.dairy_button);
        final Button fruitButton = (Button) view.findViewById(R.id.fruit_button);
        final Button vegetablesButton = (Button) view.findViewById(R.id.vegetables_button);
        final Button meatButton = (Button) view.findViewById(R.id.meat_button);
        final Button grainsButton = (Button) view.findViewById(R.id.grains_button);

        if (savedInstanceState == null || getActivity().getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            dairyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mListener.swapToItemFragment(new ItemFragment(), "Dairy");
                }
            });

            fruitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mListener.swapToItemFragment(new ItemFragment(), "Fruit");
                }
            });

            vegetablesButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mListener.swapToItemFragment(new ItemFragment(), "Vegetables");
                }
            });

            grainsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mListener.swapToItemFragment(new ItemFragment(), "Grains");
                }
            });

            meatButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mListener.swapToItemFragment(new ItemFragment(), "Meat");
                }
            });
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        return view;
    }

    /**
     * Sets listener to ItemAddListener when attached.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragment.ItemAddDatabaseListener) {
            mListener = (InventoryFragment.SwapInventoryFragListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemAddListener");
        }
    }

    /**
     * Interface for adding an item to the database.
     */
    public interface SwapInventoryFragListener {
        void swapToItemFragment(Fragment fragment, String category);
    }

}
