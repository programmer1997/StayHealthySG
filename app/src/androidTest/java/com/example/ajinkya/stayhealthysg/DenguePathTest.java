package com.example.ajinkya.stayhealthysg;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DenguePathTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void denguePathTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.dengueButton), withText("Learn More"), isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Clusters")));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Symptoms")));
        appCompatTextView2.perform(scrollTo(), click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.title), withText("Prevention")));
        appCompatTextView3.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.title), withText("Treatment")));
        appCompatTextView4.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.title), withText("Disease Clusters"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Diseases"), isDisplayed()));
        appCompatTextView6.perform(click());

    }

}
