package com.mkn.thegreatcombinator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameActivityRiddlerAITest {

    @get:Rule
    val activityRule = ActivityTestRule(GameActivityRiddlerAI::class.java)

    @Test
    fun clickableBackButton() {
        onView(withId(R.id.backButton)).perform(click())
    }

    @Test
    fun clickableGiveUpButton() {
        onView(withId(R.id.giveUpButton)).perform(click())
    }

    @Test
    fun clickableRestartButton() {
        onView(withId(R.id.restartButton)).perform(click())
    }

    @Test
    fun clickableSendAttemptButton() {
        onView(withId(R.id.sendAttemptButton)).perform(click())
    }

}