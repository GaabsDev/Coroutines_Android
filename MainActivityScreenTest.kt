package com.pliniodev.chucknorrisfacts.view.activity


import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityScreenTest {

    /**
     * Animations must be disabled in gradle.
     * testOptions { animationsDisabled = true }
     */

    lateinit var scenario: ActivityScenario<MainActivity>

    private fun startWithBundle(
        searchString: String?,
        isRandom: Boolean,
        isCategory: Boolean
    ) {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.SEARCH_MESSAGE, searchString)
        bundle.putBoolean(Constants.IS_SEARCH_BY_RANDOM, isRandom)
        bundle.putBoolean(Constants.IS_SEARCH_BY_CATEGORY, isCategory)
        scenario = ActivityScenario.launch(intent.putExtras(bundle))
    }

    @Test
    fun shouldShowImage_TextWelcome_andSearchButton_whenStart() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(2000)

        onView(withId(R.id.text_help)).check(matches(isDisplayed()))
        onView(withId(R.id.welcome_image)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_search)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldCheckIfSearchActivityWillOpen_whenClickOnMenuSearch() {
        Intents.init()
        val matcher: Matcher<Intent> = hasComponent(SearchActivity::class.java.name)
        val result = ActivityResult(Activity.RESULT_OK, null)

        scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.menu_search)).perform(click())

        intending(matcher).respondWith(result)
        intended(matcher)
        Intents.release()
    }

    @Test
    fun shouldShowRandomJokeOnRecycler_whenReceivedBundleToRandomSearch() {
        startWithBundle(searchString = null, isRandom = true, isCategory = false)
        Thread.sleep(2000)

        onView(withId(R.id.text_value)).check(matches(isDisplayed()))
        onView(withId(R.id.text_category)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowRandomJokeOnRecycler_whenReceivedBundleToRandomSearchByCategory() {
        startWithBundle(searchString = "food", isRandom = false, isCategory = true)
        Thread.sleep(2000)

        onView(withId(R.id.text_value)).check(matches(isDisplayed()))
        onView(withId(R.id.text_category)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
        onView(withText("FOOD")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowRecyclerView_whenReceivedBundleToSearchFree() {
        startWithBundle(searchString = "food", isRandom = false, isCategory = false)
        Thread.sleep(2000)

        onView(withId(R.id.facts_recycler)).check(matches(isDisplayed()))
    }
}