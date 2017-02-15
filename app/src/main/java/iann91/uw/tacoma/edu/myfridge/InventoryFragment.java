package iann91.uw.tacoma.edu.myfridge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iann91.uw.tacoma.edu.myfridge.item.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment implements ItemFragment.OnListFragmentInteractionListener {

    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState == null || getActivity().getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            ItemFragment itemFragment = new ItemFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_container, itemFragment)
                    .commit();
        }

        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onListFragmentInteraction(Item course) {

    }

}
