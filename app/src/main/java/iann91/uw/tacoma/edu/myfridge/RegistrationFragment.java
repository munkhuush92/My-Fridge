package iann91.uw.tacoma.edu.myfridge;


import android.content.Context;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    private String Email, Password, FirstName, LastName;

    private UserRegisterListener mListener;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mFirstnameEditText;
    private EditText mLastnameEditText;

    private final static String COURSE_ADD_URL
            = "http://cssgate.insttech.washington.edu/~munkh92/register.php?";

    public interface UserRegisterListener {
        public void registerUser(String url);
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserRegisterListener) {
            mListener = (UserRegisterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        mEmailEditText = (EditText) v.findViewById(R.id.email_input);
        mPasswordEditText = (EditText) v.findViewById(R.id.password_input);
        mFirstnameEditText = (EditText) v.findViewById(R.id.Firstname_input);
        mLastnameEditText = (EditText) v.findViewById(R.id.Lastname_input);


//        FloatingActionButton floatingActionButton = (FloatingActionButton)
//                getActivity().findViewById(R.id.fab);
//        floatingActionButton.show();


        Button registerButton = (Button) v.findViewById(R.id.fragRegisterbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.registerUser(url);
            }
        });

        return v;
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {

            String personEmail = mEmailEditText.getText().toString();
            sb.append("email=");
            sb.append(personEmail);


            String personPass = mPasswordEditText.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(personPass, "UTF-8"));


            String personFirstname = mFirstnameEditText.getText().toString();
            sb.append("&firstname=");
            sb.append(URLEncoder.encode(personFirstname, "UTF-8"));

            String personLastname = mLastnameEditText.getText().toString();
            sb.append("&lastname=");
            sb.append(URLEncoder.encode(personLastname, "UTF-8"));

            Log.i("REgister user", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }



}
