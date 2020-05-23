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
class GameActivitySolverAITest {

    @get:Rule
    val activityRule = ActivityTestRule(GameActivitySolverAI::class.java)

    @Test
    fun clickableBackButton() {
        onView(withId(R.id.backButton)).perform(click())
    }

    @Test
    fun clickableRestartButton() {
        onView(withId(R.id.restartButton)).perform(click())
    }

    @Test
    fun clickableReplyButton() {
        onView(withId(R.id.replyButton)).perform(click())
    }

    @Test
    fun clickableSigns() {
        onView(withId(R.id.leftMinus)).perform(click())
        onView(withId(R.id.rightMinus)).perform(click())
        onView(withId(R.id.leftPlus)).perform(click())
        onView(withId(R.id.rightPlus)).perform(click())
    }

}