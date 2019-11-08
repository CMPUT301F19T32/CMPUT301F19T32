package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;

/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MainActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Add a username to the EditText view and check the username using assertTrue

     */
    @Test
    public void checkSignIn(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // test empty input
        solo.enterText((EditText) solo.getView(R.id.username_field), "");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_field));

        // test wrong username
        solo.enterText((EditText) solo.getView(R.id.username_field), "WrongUser");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


        // test wrong password
        solo.enterText((EditText) solo.getView(R.id.password_field), "password");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));

        // input correct username
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // test wrong password
        solo.enterText((EditText) solo.getView(R.id.password_field), "WrongPassword");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.password_field));

        // test empty password
        solo.enterText((EditText) solo.getView(R.id.password_field), "");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.password_field));

        // test correct password
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        //solo.assertCurrentActivity("Wrong Activity", HomePage.class);




    }




}

