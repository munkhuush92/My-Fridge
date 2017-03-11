package iann91.uw.tacoma.edu.myfridge;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import iann91.uw.tacoma.edu.myfridge.Authenticate.LoginActivity;
import iann91.uw.tacoma.edu.myfridge.testing.SharedPreferencesHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by admin on 3/9/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {



    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test.
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);




    @Test
    public void testRegister() {

        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        onView(withId(R.id.registration_button)).perform(click());
        // Type text and then press the button.
        onView(withId(R.id.email_input))
                .perform(typeText(email));
        onView(withId(R.id.password_input))
                .perform(typeText("test1@#"));
        onView(withId(R.id.fragRegisterbutton))
                .perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Registration is successful!"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testLogin() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.email))
                .perform(typeText("ian_nicho@gmail.com"));
        onView(withId(R.id.password))
                .perform(typeText("123456"));

        onView(withId(R.id.login_button))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.logout_button_dash))
                .perform(click());

    }


    @Test
    public void testRegisterInvalidEmail() {
        // Type text and then press the button.
        onView(withId(R.id.registration_button)).perform(click());
        onView(withId(R.id.email_input))
                .perform(typeText("mmuppauw.edu"));
        onView(withId(R.id.password_input))
                .perform(typeText("test1@#"));
        onView(withId(R.id.fragRegisterbutton))
                .perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Failed to register: Please enter a valid email."))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testRegisterInvalidPassword() {
        // Type text and then press the button.
        onView(withId(R.id.registration_button)).perform(click());
        onView(withId(R.id.email_input))
                .perform(typeText("mmuppa@uw.edu"));
        onView(withId(R.id.password_input))
                .perform(typeText(""));
        onView(withId(R.id.fragRegisterbutton))
                .perform(click());

        onView(withText("Failed to register: Please enter a password with longer than five character!"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }



}
