package com.example.myapplication;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for HomePageActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class HomePageTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    /**
     * Runs before all tests
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }


    /**
     * sign in and check if the username appear on the page
     */
    @Test
    public void usernameTest(){
        //go to homepage activity after sign in
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", HomePage.class);

        //check the username str
        assertTrue(solo.searchText("user1"));
    }

    /**
     * sign in the account test_filter
     * check the visibility of buttons after press Filter button
     * check if the filter happy works
     * check if the filter sad works
     * check if the filter angry works
     * check if the filter all works
     */
    @Test
    public void filterTest(){
        //go to homepage activity after sign in
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_field), "test_filter");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", HomePage.class);

        //check visibility
        solo.clickOnButton("Filter");
        solo.waitForText("ALL", 1, 2000);
        solo.waitForText("ANGRY", 1, 2000);
        solo.waitForText("HAPPY", 1, 2000);
        solo.waitForText("SAD", 1, 2000);

        //check filter happy
        solo.clickOnButton("Happy");
        assertFalse(solo.waitForText("angry", 1, 2000));
        assertFalse(solo.waitForText("sad", 1, 2000));
        assertTrue(solo.waitForText("happy", 1, 2000));

        //check filter sad
        solo.clickOnButton("Filter");
        solo.clickOnButton("Sad");
        assertFalse(solo.waitForText("angry", 1, 2000));
        assertTrue(solo.waitForText("sad", 1, 2000));
        assertFalse(solo.waitForText("happy", 1, 2000));

        //check filter angry
        solo.clickOnButton("Filter");
        solo.clickOnButton("Angry");
        assertTrue(solo.waitForText("angry", 1, 2000));
        assertFalse(solo.waitForText("sad", 1, 2000));
        assertFalse(solo.waitForText("happy", 1, 2000));

        //check filter all
        solo.clickOnButton("Filter");
        solo.clickOnButton("All");
        assertTrue(solo.waitForText("angry", 1, 2000));
        assertTrue(solo.waitForText("sad", 1, 2000));
        assertTrue(solo.waitForText("happy", 1, 2000));
    }
}
