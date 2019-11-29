package com.example.myapplication;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;

public class AddMoodActivityTest {
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
        solo.clickOnView(solo.getView((R.id.button)));
        View view1 = solo.getView("imageView");

        solo.clickOnView(view1);
        GridView gridview = (GridView) solo.getView("gridview");
        ImageView img = (ImageView) gridview.getChildAt(0);
        solo.clickOnView(img);

        solo.enterText((EditText) solo.getView(R.id.reason), "heavy school work");

        // 0 is the first spinner in the layout
        View view3 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view3);

        solo.clickOnButton("submit");

        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);


    }
}
