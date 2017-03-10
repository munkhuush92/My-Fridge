package iann91.uw.tacoma.edu.myfridge;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import iann91.uw.tacoma.edu.myfridge.testing.SharedPreferenceEntry;
import iann91.uw.tacoma.edu.myfridge.testing.SharedPreferencesHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;


/**
 * Unit tests for the {@link SharedPreferencesHelper} that
 * mocks {@link SharedPreferences}.
 */

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final boolean TEST_LOGGED_IN = true;
    private static final String TEST_EMAIL = "test@email.com";

    private SharedPreferenceEntry mSharedPreferenceEntry;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void initMocks() {
        // Create SharedPreferenceEntry to persist.
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_LOGGED_IN,
                TEST_EMAIL);

        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference();

        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // Save the personal information to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);

        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, is(true));

        // Read personal information from SharedPreferences
        SharedPreferenceEntry savedSharedPreferenceEntry =
                mMockSharedPreferencesHelper.getLoginInfo();

        // Make sure both written and retrieved login information are equal.
        assertThat("Checking that SharedPreferenceEntry.isLogged has been persisted and read correctly",
                mSharedPreferenceEntry.isLoggedIn(),
                is(equalTo(savedSharedPreferenceEntry.isLoggedIn())));
        assertThat("Checking that SharedPreferenceEntry.email has been persisted and read "
                        + "correctly",
                mSharedPreferenceEntry.getEmail(),
                is(equalTo(savedSharedPreferenceEntry.getEmail())));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // Read login information from a broken SharedPreferencesHelper
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success,
                is(false));
    }

    /**
     * Creates a mocked SharedPreferences.
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPreferences.getBoolean(eq(SharedPreferencesHelper.KEY_LOGGED_IN), anyBoolean()))
                .thenReturn(mSharedPreferenceEntry.isLoggedIn());
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString()))
                .thenReturn(mSharedPreferenceEntry.getEmail());

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // Mocking a commit that fails.
        when(mMockBrokenEditor.commit()).thenReturn(false);

        // Return the broken MockEditor when requesting it.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}
