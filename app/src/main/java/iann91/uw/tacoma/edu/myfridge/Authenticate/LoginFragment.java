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
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import iann91.uw.tacoma.edu.myfridge.Dashboard.DashboardActivity;
import iann91.uw.tacoma.edu.myfridge.R;

/**
 * Login fragment for handling logging in to the app.
 * @author iann91 Munkh92
 * @version 1.0
 */
public class LoginFragment extends Fragment {

    private UserLoginTask mAuthTask = null;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private OnListFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Initializes fields and updates view for login fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container,
                false);

        mEmailView = (EditText) view.findViewById(R.id.email);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) view.findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);
        TextView buttonRegistration = (TextView) view.findViewById(R.id.registration_button);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.registration_button:
                        showOtherFragment();
                        break;
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Attempts to login to the app.
     * Creates and executes a login Async task.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Checks to make sure email is valid.
     * @param email email entered.
     * @return whether or not email is valid.
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Checks to make sure password is valid.
     * @param password password entered.
     * @return whether or not password is valid.
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            HttpURLConnection urlConnection = null;
            try {
                StringBuilder builder = new StringBuilder();
                builder.append("http://cssgate.insttech.washington.edu/~iann91/login.php?email=");
                builder.append(mEmail);
                builder.append("&password=");
                builder.append(mPassword);

                URL url = new URL(builder.toString());


                urlConnection = (HttpURLConnection) url.openConnection();


                InputStream content = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to login, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

        return response;
        }

        /**
         * This method takes the result and it assigns input
         * to interface method login. This puts email and pass in the extra
         * and takes the user to next Activity.
         * @param s result string from doInBackground method
         */
        @Override
        protected void onPostExecute(String s) {
            mAuthTask = null;
            String err=null;

            showProgress(false);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String status = (String) jsonObject.get("result");

                if (status.equals("success")) {
                    int id = (int) jsonObject.get("id");
                    mListener.login(mEmail, id);
                    //getActivity().finish();
                } else {
                    String error = (String) jsonObject.get("error");
                    if(error.equals("Incorrect password.")) {
                        Toast.makeText( getActivity(),"Incorrect Password", Toast.LENGTH_LONG).show();
                        mPasswordView.requestFocus();
                    } else {
                        Toast.makeText( getActivity(),"Incorrect Email", Toast.LENGTH_LONG).show();
                        mEmailView.requestFocus();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
                Toast.makeText( getActivity(),"Login Failed", Toast.LENGTH_LONG).show();
                            mEmailView.requestFocus();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Initializes the Listener when fragment attaches.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * Swaps between login and registration fragment.
     */
    public void showOtherFragment()
    {
        Fragment registrationFragment = new RegistrationFragment();
        mListener = (OnListFragmentInteractionListener)getActivity();
        mListener.onListFragmentInteraction(registrationFragment);
    }

    /**
     * Uninitializes listener when it detaches.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Fragment fragment);
        void login(String email, int id);
    }

}
