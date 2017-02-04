package iann91.uw.tacoma.edu.myfridge;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    private EditText email, password, firstname, lastname;
    private String Email, Password, FirstName, LastName;

    private Button myRegisterButton;
    private LoginFragment.OnListFragmentInteractionListener mListener;

    public RegistrationFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container,
                false);
        // Inflate the layout for this fragment
        myRegisterButton = (Button) view.findViewById(R.id.button);
        email = (EditText) view.findViewById(R.id.email_input);
        password = (EditText) view.findViewById(R.id.password_input);
        firstname = (EditText) view.findViewById(R.id.Firstname_input);
        lastname = (EditText) view.findViewById(R.id.Lastname_input);

        myRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = email.getText().toString();
                Password = password.getText().toString();
                FirstName = firstname.getText().toString();
                LastName = lastname.getText().toString();
                BackGround b = new BackGround();
                b.execute(Email, Password, FirstName, LastName);
                showOtherFragment();
            }
        });
        return view;
    }

    public void showOtherFragment()
    {
        Fragment loginFragment = new LoginFragment();
        mListener = (LoginFragment.OnListFragmentInteractionListener)getActivity();
        mListener.onListFragmentInteraction(loginFragment);
    }


    class BackGround extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String first_name = params[2];
            String last_name = params[3];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://students.washington.edu/munkh92/test/register.php");
                String urlParams = "email=" + email + "&password=" + password +
                        "&first_name=" + first_name + "&last_name" + last_name;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                System.out.println(is.toString());
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }
    }

}