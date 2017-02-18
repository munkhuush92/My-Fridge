package iann91.uw.tacoma.edu.myfridge.Inventory;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import iann91.uw.tacoma.edu.myfridge.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {

    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inventory, container,
                false);

        final Button itemsButton = (Button) view.findViewById(R.id.items_button);

        if (savedInstanceState == null || getActivity().getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            itemsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ItemFragment itemFragment = new ItemFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_dashboard, itemFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        return view;
    }


}
