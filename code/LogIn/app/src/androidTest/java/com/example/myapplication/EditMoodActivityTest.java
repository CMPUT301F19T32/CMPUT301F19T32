package com.example.myapplication;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class EditMoodActivityTest {
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

    @Test
    public void checkEditMoodList(){
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.clickInList(1);

        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);


    }
}
