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
 * Test class for SignUpActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class SignUpActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignUpActivity> rule =
            new ActivityTestRule<>(SignUpActivity.class, true, true);
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
    public void checkSignUp(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_field), "UItest1");
        solo.clickOnButton("SIGN UP"); //Select SIGN UP Button
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.password_field), "123");
        solo.clickOnButton("SIGN UP"); //Select SIGN UP Button
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.passwordConfirm_field), "234");
        solo.clickOnButton("SIGN UP"); //Select SIGN UP Button
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.passwordConfirm_field)); //Clear the EditText


        solo.enterText((EditText) solo.getView(R.id.passwordConfirm_field), "123");
        solo.clickOnButton("SIGN UP"); //Select SIGN UP Button
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        deleteTestAccount("UItest1");

    }

    // delete the test account
    public void deleteTestAccount(String key){
        FirebaseFirestore db;
        final String TAG = "sample";
        db = FirebaseFirestore.getInstance();
        db.collection("Account").document(key)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }


}

