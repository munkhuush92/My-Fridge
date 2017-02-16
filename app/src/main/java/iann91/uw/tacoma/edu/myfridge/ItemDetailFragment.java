package iann91.uw.tacoma.edu.myfridge;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import iann91.uw.tacoma.edu.myfridge.item.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    public final static String COURSE_ITEM_SELECTED = "course_selected";

    private TextView mItemIdTextView;
    private TextView mItemNameTextView;
    private TextView mItemQuantityTextView;
    private TextView mItemTypeTextView;


    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_item_detail, container, false);
        mItemIdTextView = (TextView) view.findViewById(R.id.item_id);
        mItemNameTextView = (TextView) view.findViewById(R.id.item_name);
        mItemQuantityTextView = (TextView) view.findViewById(R.id.item_quantity);
        mItemTypeTextView = (TextView) view.findViewById(R.id.item_type);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        return view;
    }

    public void updateView(Item item) {
        if (item != null) {
            mItemIdTextView.setText("Item id: " + item.getmItemId());
            mItemNameTextView.setText("Name: " + item.getmItemName());
            mItemQuantityTextView.setText("Quantity: " + item.getmItemQuantity());
            mItemTypeTextView.setText("Type: " + item.getmItemType());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((Item) args.getSerializable(COURSE_ITEM_SELECTED));
        }
    }


}
