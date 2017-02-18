package iann91.uw.tacoma.edu.myfridge.Inventory;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.item.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    public final static String COURSE_ITEM_SELECTED = "course_selected";
    private final static String ITEM_UPDATE_URL
            = "http://cssgate.insttech.washington.edu/~iann91/updateItem.php?";
    private final static String ITEM_DELETE_URL
            = "http://cssgate.insttech.washington.edu/~iann91/deleteItem.php?";

    private EditText mItemNameTextView;
    private EditText mItemQuantityTextView;
    private EditText mItemTypeTextView;
    private ItemAddListener mListener;
    private String mItemName, mItemQuantity, mItemType;
    private int mPersonID;
    private Item mItem;


    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_item_detail, container, false);
        mItemNameTextView = (EditText) v.findViewById(R.id.item_name);
        mItemQuantityTextView = (EditText) v.findViewById(R.id.item_quantity);
        mItemTypeTextView = (EditText) v.findViewById(R.id.item_type);
        mPersonID =  getActivity().getIntent().getIntExtra("id", 0);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        Button updateItemButton = (Button) v.findViewById(R.id.update_item_button);
        updateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItem.setmItemName(mItemNameTextView.getText().toString());
                mItem.setmItemQuantity(mItemQuantityTextView.getText().toString());
                mItem.setmItemType(mItemTypeTextView.getText().toString());
                updateView(mItem);
                String url = buildCourseURL(v, ITEM_UPDATE_URL);
                mListener.addItem(url);
            }
        });

        Button deleteItemButton = (Button) v.findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v, ITEM_DELETE_URL);
                mListener.addItem(url);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragment.ItemAddListener) {
            mListener = (ItemDetailFragment.ItemAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemAddListener");
        }
    }

    public void updateView(Item item) {
        if (item != null) {
            mItemNameTextView.setText(item.getmItemName());
            mItemQuantityTextView.setText(item.getmItemQuantity());
            mItemTypeTextView.setText(item.getmItemType());


            mItemName = item.getmItemName();
            mItemName = mItemName.replaceAll(" ", "%20");
            mItemQuantity = item.getmItemQuantity();
            mItemQuantity = mItemQuantity.replaceAll(" ", "%20");
            mItemType = item.getmItemType();
            mItemType = mItemType.replaceAll(" ", "%20");
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
            mItem = (Item) args.getSerializable(COURSE_ITEM_SELECTED);
            updateView(mItem);
        }
    }

    private String buildCourseURL(View v, String type) {

        StringBuilder sb = new StringBuilder(type);

        try {
            sb.append("nameFoodItem=");
            sb.append(mItemName);
            sb.append("&sizeFoodItem=");
            sb.append(mItemQuantity);
            sb.append("&PersonID=");
            sb.append(mPersonID);
            sb.append("&foodType=");
            sb.append(mItemType);
            Log.i("CHECK!!!", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


    public interface ItemAddListener {
        public void addItem(String url);
    }


}
