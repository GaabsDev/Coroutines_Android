package com.pliniodev.chucknorrisfacts.view.activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.pliniodev.chucknorrisfacts.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class SearchActivityScreenTest {

    /**
     * Animations must be disabled in gradle.
     * testOptions { animationsDisabled = true }
     */

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<SearchActivity> =
        ActivityScenarioRule(SearchActivity::class.java)

    @Test
    fun shouldShowViewsOnScreen_whenStart() {
        onView(withText("Search a fact about Chuck Norris")).check(matches(isDisplayed()))
        onView(withText("What do you prefer?")).check(matches(isDisplayed()))
        onView(withText("Give me a random Joke!")).check(matches(isDisplayed()))
        onView(withText("I want a random joke of category:")).check(matches(isDisplayed()))
        onView(withText("Let me free to search!")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowViewDropDownAndButton_whenRadioCategoryClicked() {
        onView(withId(R.id.radio_category)).perform(click())
        onView(withText("SEARCH")).check(matches(isDisplayed()))
        onView(withId(R.id.edit_list_category)).check(matches(isDisplayed()))
        onView(withId(R.id.layout_to_write_msg)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowViewEditAndButton_whenRadioFreeClicked() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withText("SEARCH")).check(matches(isDisplayed()))
        onView(withId(R.id.edit_search)).check(matches(isDisplayed()))
        onView(withId(R.id.layout_to_write_msg)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowTextToUserWriteSomeSearch_whenRadioFreeClicked() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(
            allOf(
                withId(R.id.textinput_helper_text), withText("C'mon search something!"),
                withParent(withParent(instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowTextMinThreeCharacters_whenOneCharacterIsTyped() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withId(R.id.edit_search)).perform(typeText("f"))
        onView(
            allOf(
                withId(R.id.textinput_helper_text), withText("Min. 3 characters."),
                withParent(withParent(instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowTextItsASearch_whenSearchIsValidTyped() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withId(R.id.edit_search)).perform(typeText("fff"))
        onView(
            allOf(
                withId(R.id.textinput_helper_text), withText("Yeah! Its a search."),
                withParent(withParent(instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowTextNoSymbols_whenSearchWithSymbolsTyped() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withId(R.id.edit_search)).perform(typeText("fff."))
        onView(
            allOf(
                withId(R.id.textinput_helper_text),
                withText("No symbols, just letter or numbers ok?"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowTextMax120Characters_whenLargeTextIsTyped() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withId(R.id.edit_search)).perform(
            replaceText(
                "Lorem ipsum dolor sit amet  consectetur adipiscing eli  sed\n"
                        + " do eiusmod tempor incididunt asdasdasdasdasd"
            )
        )
        onView(
            allOf(
                withId(R.id.textinput_helper_text), withText("Max.120 characters."),
                withParent(withParent(instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowTextMaxThreeCharacters_whenLargeTextIsTypjghed() {
        onView(withId(R.id.radio_free)).perform(click())
        onView(withId(R.id.edit_search)).perform(
            replaceText(
                "Lorem ipsum dolor sit amet  consectetur adipiscing eli  sed\n"
                        + " do eiusmod tempor incididunt asdasdasdasdasd"
            )
        )
        onView(
            allOf(
                withId(R.id.textinput_helper_text), withText("Max.120 characters."),
                withParent(withParent(instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
    }


    @Test
    fun shouldCheckIfMainActivityWillOpen_whenClickOnRadioRandom() {
        Intents.init()

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        activityScenarioRule = ActivityScenarioRule(intent)

        onView(withId(R.id.radio_random)).perform(click())
        val matcher: Matcher<Intent> = IntentMatchers.hasComponent(MainActivity::class.java.name)

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        Intents.intending(matcher).respondWith(result)

        intended(matcher)
        Intents.release()
    }
}