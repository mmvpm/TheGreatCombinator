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
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickableInfoButton() {
        onView(withId(R.id.infoButton)).perform(click())
    }

    @Test
    fun clickableSettingsButton() {
        onView(withId(R.id.settingsButton)).perform(click())
    }

    @Test
    fun clickableToActivitySolverAI() {
        onView(withId(R.id.toActivitySolverAI)).perform(click())
    }

    @Test
    fun clickableToActivityRiddlerAI() {
        onView(withId(R.id.toActivityRiddlerAI)).perform(click())
    }

}