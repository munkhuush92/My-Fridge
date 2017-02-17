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


    private CourseAddListener mListener;
    private final static String ITEM_ADD_URL
            = "http://cssgate.insttech.washington.edu/~iann91/addItem.php?";
    private EditText mItemNameEditText;
    private EditText mItemQuantityEditText;
    private EditText mFoodTypeEditText;


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

        mItemNameEditText = (EditText) v.findViewById(R.id.add_item_id);
        mItemQuantityEditText = (EditText) v.findViewById(R.id.add_item_quantity);
        mFoodTypeEditText = (EditText) v.findViewById(R.id.add_item_type);



        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addItemButton = (Button) v.findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addItem(url);
            }
        });

        return v;
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(ITEM_ADD_URL);

        try {

            String courseId = mCourseIdEditText.getText().toString();
            sb.append("id=");
            sb.append(courseId);


            String courseShortDesc = mCourseShortDescEditText.getText().toString();
            sb.append("&shortDesc=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));


            String courseLongDesc = mCourseLongDescEditText.getText().toString();
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));

            String coursePrereqs = mCoursePrereqsEditText.getText().toString();
            sb.append("&prereqs=");
            sb.append(URLEncoder.encode(coursePrereqs, "UTF-8"));

            Log.i("CourseAddFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


    public interface CourseAddListener {
        public void addCourse(String url);
    }

}
