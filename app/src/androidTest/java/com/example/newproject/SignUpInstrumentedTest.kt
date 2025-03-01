package com.example.newproject

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.newproject.ui.activity.RegisterActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUpInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun testUserRegistration() {
        // Enter First Name
        onView(withId(R.id.registerFname)).perform(typeText("Tom"))

        // Enter Last Name
        onView(withId(R.id.registerLname)).perform(typeText("Doe"))

        // Enter Address
        onView(withId(R.id.registerAddress)).perform(typeText("Kathmandu"))

        // Enter Contact
        onView(withId(R.id.registerContact)).perform(typeText("9875632564"))

        // Enter Email
        onView(withId(R.id.registerEmail)).perform(typeText("tom.doe@example.com"))

        // Enter Password
        onView(withId(R.id.registerPassword)).perform(typeText("password123"))

        // Close keyboard
        closeSoftKeyboard()

        // Wait for UI update
        Thread.sleep(1500)

        // Click Signup Button
        onView(withId(R.id.btnRegister)).perform(click())

        // Wait for response
        Thread.sleep(3000)
    }
}