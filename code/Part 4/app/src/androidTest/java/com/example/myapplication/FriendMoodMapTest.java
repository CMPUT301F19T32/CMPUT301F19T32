package com.example.myapplication;


import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for FriendMoodMapTest Activity. All the UI tests are written here. Robotium test framework is
 used
 */


public class FriendMoodMapTest {
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
     * input specific account to test map function

     */
    @Test
    public void goToFiendMap(){
        solo.enterText((EditText) solo.getView(R.id.username_field), "puquan");
        solo.enterText((EditText) solo.getView(R.id.password_field),"123");
        solo.clickOnButton("SIGN IN");

        solo.assertCurrentActivity("Wrong Activity", HomePage.class);
        solo.clickOnButton("Followed");
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);
        solo.clickOnButton("MAP");
        solo.assertCurrentActivity("Wrong Activity", FriendMoodMap.class);
    }


}
