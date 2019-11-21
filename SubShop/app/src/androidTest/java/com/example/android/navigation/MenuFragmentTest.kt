package com.example.android.navigation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuFragmentTest {

    @get:Rule
    var
    mainActivity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun CompleteRun(){
        //Tests a complete run as a user should do it
        onView(withId(R.id.orderButton)).perform(click())
        onView(withId(R.id.philly)).perform(click())
        onView(withId(R.id.white)).perform(click())
        onView(withId(R.id.checkBox2)).perform(click())
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.submitButton)).perform(click())
        onView(withId(R.id.doneButton)).perform(click())
    }

    @Test
    fun ValuesChecker(){
        onView(withId(R.id.orderButton)).perform(click())

        //Checks the base values
        onView(withId(R.id.philly)).perform(click())
        onView(withId(R.id.currentSelection)).check(matches(withText("Sandwich")))
        onView(withId(R.id.currentPrice)).check(matches(withText("$0.00")))

        //Checks that the submit button is not yet visible
        onView(withId(R.id.submitButton)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.white)).perform(click())
        onView(withId(R.id.submitButton)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))

        //Checks that the submit button is now visible and that the text has updated to the correct values for Philly
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.submitButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.currentSelection)).check(matches(withText("Philly")))
        onView(withId(R.id.currentPrice)).check(matches(withText("$6.50")))

        //Checks with 1 topping added
        onView(withId(R.id.checkBox1)).perform(click())
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.currentPrice)).check(matches(withText("$7.00")))

        //Checks with all toppings added
        onView(withId(R.id.checkBox2)).perform(click())
        onView(withId(R.id.checkBox3)).perform(click())
        onView(withId(R.id.checkBox4)).perform(click())
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.currentPrice)).check(matches(withText("$8.50")))

        //Checks the BLT is correct
        onView(withId(R.id.blt)).perform(click())
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.currentSelection)).check(matches(withText("BLT")))
        onView(withId(R.id.currentPrice)).check(matches(withText("$7.00")))

        //Checks that Spicy Italian is Correct
        onView(withId(R.id.spicyItalian)).perform(click())
        onView(withId(R.id.testButton)).perform(click())
        onView(withId(R.id.currentSelection)).check(matches(withText("Spicy Italian")))
        onView(withId(R.id.currentPrice)).check(matches(withText("$7.75")))

        //Checks that the values are also correct on the receipt screen indicating that the values passed correctly
        onView(withId(R.id.submitButton)).perform(click())
        onView(withId(R.id.sandwichText)).check(matches(withText("Spicy Italian")))
        onView(withId(R.id.sandwichPrice)).check(matches(withText("$5.75")))
        onView(withId(R.id.sandwichPrice2)).check(matches(withText("$7.75")))

        onView(withId(R.id.doneButton)).perform(click())
    }

//    onView(<view_matcher>).perform(<view_action>)
//    onView(<view_matcher>).check(<view_assertion>(<view_matcher))
//    onView(<view_matcher>).perform(<view_action>).check(<view_assertion>(<view_matcher))

}