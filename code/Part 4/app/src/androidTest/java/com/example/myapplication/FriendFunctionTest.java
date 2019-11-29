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
 * Test class for FriendActivity, add, request, . All the UI tests are written here. Robotium test framework is
 used
 */
public class FriendFunctionTest {
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
     * run for app to get friendPage
     * to tell if the button lead app to friend page.
     */
    @Test
    public void CheckFriendShow() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.assertCurrentActivity("Wrong Activity", HomePage.class);
        solo.clickOnButton("Followed");
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);
    }

    /**
     * run for app to show if the friend is show.
     * the user1 can see Zhob last mood sine Zhob allow user1 to see.
     * check if the mood for zhob is show when click
     */
    @Test
    public void CheckFriendMood() {
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.clickOnButton("Followed");
        assertTrue(solo.waitForText("Zhob", 1, 2000));
        solo.clickInList(1);
        assertTrue(solo.waitForText("Zhob", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ViewActivity.class);
    }

    /**
     * run to go to RequestActivity from FriendActivity
     * Wu is sent a request with message 123.
     * Check if get Wu's request and click on it.
     * Check the message too.
     */
    @Test
    public void CheckRequest() {
        solo.enterText((EditText) solo.getView(R.id.username_field), "test10");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.clickOnButton("Followed");
        solo.clickOnButton("Request");
        solo.assertCurrentActivity("Wrong Activity", RequestActivity.class);
        assertTrue(solo.waitForText("Wu", 1, 2000));
        solo.clickInList(1);
        assertTrue(solo.waitForText("Wu", 1, 2000));
        assertTrue(solo.waitForText("123", 1, 2000));
    }

    /**
     * This test the add friend Function.
     * with sent a request to Wu(not in firestore, the part of test on firestore are tested
     * by hand.
     */
    @Test
    public void CheckAddfriend() {
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.clickOnButton("Followed");
        solo.clickOnButton("ADDFRIEND");
        solo.enterText((EditText) solo.getView(R.id.UserNameToSent), "Wu");
        solo.enterText((EditText) solo.getView(R.id.MassageToSent), "123");
        solo.clickOnButton("Sent");
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);

    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}