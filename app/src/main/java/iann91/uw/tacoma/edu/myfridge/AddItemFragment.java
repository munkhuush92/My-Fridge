package iann91.uw.tacoma.edu.myfridge;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private ItemAddListener mListener;

    //private ItemAddListener mListener;
    private final static String ITEM_ADD_URL
            = "http://cssgate.insttech.washington.edu/~iann91/addItem.php?";
    private final static String ITEM_UPDATE_URL
            = "http://cssgate.insttech.washington.edu/~iann91/updateItem.php?";
    private EditText mItemNameEditText;
    private EditText mItemQuantityEditText;
    private EditText mFoodTypeEditText;
    private int mPersonID;


    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemAddListener) {
            mListener = (ItemAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }



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
                mListener.addItem(url);
            }
        });

        Button updateItemButton = (Button) v.findViewById(R.id.update_item_button);
        updateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v, ITEM_UPDATE_URL);
                mListener.addItem(url);
            }
        });

        return v;
    }

    private String buildCourseURL(View v, String type) {

        StringBuilder sb = new StringBuilder(type);

        try {
            String courseShortDesc = mItemNameEditText.getText().toString();
            sb.append("&nameFoodItem=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));
            String courseLongDesc = mItemQuantityEditText.getText().toString();
            sb.append("&sizeFoodItem=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));
            sb.append("&PersonID=" + mPersonID);
            String coursePrereqs = mFoodTypeEditText.getText().toString();
            sb.append("&foodType=");
            sb.append(URLEncoder.encode(coursePrereqs, "UTF-8"));

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
