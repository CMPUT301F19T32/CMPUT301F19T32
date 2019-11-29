package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

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
public class AddMoodActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<AddMoodActivity> rule =
            new ActivityTestRule<>(AddMoodActivity.class, true, true);
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
     * Add a mood to the EditText view and check the username using assertTrue
     */
    @Test
    public void checkAddMood(){
        /*
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1");
        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN IN");
        solo.clickOnButton("ADD MOOD");
        */
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);

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





    }




}
