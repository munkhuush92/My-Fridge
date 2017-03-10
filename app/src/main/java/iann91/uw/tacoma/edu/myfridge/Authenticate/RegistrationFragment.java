package iann91.uw.tacoma.edu.myfridge.Authenticate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import iann91.uw.tacoma.edu.myfridge.R;


/**
 * Fragment for registering account.
 * @author iann91 Munkh92
 * @version 1.0
 */
public class RegistrationFragment extends Fragment {
    private String Email, Password, FirstName, LastName;

    private UserRegisterListener mListener;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mFirstnameEditText;
    private EditText mLastnameEditText;

    private final static String REGISTRATION_URL
            = "http://cssgate.insttech.washington.edu/~munkh92/register.php?";

    /**
     * Interface for registering the user.
     */
    public interface UserRegisterListener {
        public void registerUser(String url);
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Initializes the listener when attaching.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserRegisterListener) {
            mListener = (UserRegisterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UserRegistrationListener");
        }
    }

    /**
     * Initializes fields and sets up views for registration fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        mEmailEditText = (EditText) v.findViewById(R.id.email_input);
        mPasswordEditText = (EditText) v.findViewById(R.id.password_input);
        mFirstnameEditText = (EditText) v.findViewById(R.id.Firstname_input);
        mLastnameEditText = (EditText) v.findViewById(R.id.Lastname_input);


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

    /**
     * Builds the url for registration.
     * @param
     * @return registration url.
     */
    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(REGISTRATION_URL);

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