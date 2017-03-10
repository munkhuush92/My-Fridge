package iann91.uw.tacoma.edu.myfridge.testing;

/**
 * Model class containing personal information that will be
 * saved to SharedPreferences.
 * Add to this class any other app related information
 * that needs to be cached.
 */
public class SharedPreferenceEntry {
    // Name of the user.
    private boolean mIsLoggedIn = false;

    // Email address of the user.
    private final String mEmail;

    // Add others here..

    public SharedPreferenceEntry(boolean loggedIn, String email) {
        mIsLoggedIn = loggedIn;
        mEmail = email;
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public String getEmail() {
        return mEmail;
    }

}
