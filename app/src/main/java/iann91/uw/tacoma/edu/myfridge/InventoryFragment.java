package iann91.uw.tacoma.edu.myfridge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {

    private InventoryFragment.OnListFragmentInteractionListener mListener;

    public InventoryFragment() {
        // Required empty public constructor
    }

    public void showOtherFragment()
    {
        Fragment registrationFragment = new RegistrationFragment();
        mListener = (InventoryFragment.OnListFragmentInteractionListener)getActivity();
        mListener.onListFragmentInteraction(registrationFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Fragment fragment);
    }

}
