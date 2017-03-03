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

import java.net.URLEncoder;
import java.util.ArrayList;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.item.Item;


/**
 * Fragment for adding an item to the database.
 * @author imnich91 munkh92
 * @version 1.0
 */
public class AddItemFragment extends Fragment {

    private ItemAddDatabaseListener mListener;
    private ItemAddLocallyListener mLocalListener;

    //private ItemAddListener mListener;
    private final static String ITEM_ADD_URL
            = "http://cssgate.insttech.washington.edu/~iann91/addItem.php?";
    private EditText mItemNameEditText;
    private EditText mItemQuantityEditText;
    private EditText mFoodTypeEditText;
    private int mPersonID;
    private Item mItem;


    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Sets listener to ItemAddListener when attached.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemAddDatabaseListener) {
            mListener = (ItemAddDatabaseListener) context;
            mLocalListener = (ItemAddLocallyListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemAddListener");
        }
    }


    /**
     * Initializes necessary fields and views for fragment to display.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_item, container, false);

        mItemNameEditText = (EditText) v.findViewById(R.id.add_item_name);
        mItemQuantityEditText = (EditText) v.findViewById(R.id.add_item_quantity);
        mFoodTypeEditText = (EditText) v.findViewById(R.id.add_item_type);
        mPersonID =  getActivity().getIntent().getIntExtra("id", 0);



        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addItemButton = (Button) v.findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v, ITEM_ADD_URL);
                mListener.addItemDatabase(url);
                mLocalListener.addItem(mItem);
            }
        });
        return v;
    }

    /**
     * Builds the url for adding an item to the database.
     * @param v
     * @param url url to add parameters to.
     * @return url for adding item.
     */
    private String buildCourseURL(View v, String url) {

        StringBuilder sb = new StringBuilder(url);

        try {
            String itemName = mItemNameEditText.getText().toString();
            sb.append("&nameFoodItem=");
            sb.append(URLEncoder.encode(itemName, "UTF-8"));
            String itemQuantity = mItemQuantityEditText.getText().toString();
            sb.append("&sizeFoodItem=");
            sb.append(URLEncoder.encode(itemQuantity, "UTF-8"));
            sb.append("&PersonID=" + mPersonID);
            String foodType = mFoodTypeEditText.getText().toString();
            sb.append("&foodType=");
            sb.append(URLEncoder.encode(foodType, "UTF-8"));
            mItem = new Item(mItemNameEditText.getText().toString(),
                    mItemQuantityEditText.getText().toString(), ""+mPersonID, mFoodTypeEditText.getText().toString());
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Interface for adding an item to the database.
     */
    public interface ItemAddDatabaseListener {
        public void addItemDatabase(String url);
    }

    public interface ItemAddLocallyListener {
        public void addItem(Item item);
    }
}
