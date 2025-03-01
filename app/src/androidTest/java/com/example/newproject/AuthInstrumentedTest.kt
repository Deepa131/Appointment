package com.example.newproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.test.espresso.Espresso.closeSoftKeyboard
import com.example.newproject.ui.activity.LoginActivity

@LargeTest
@RunWith(AndroidJUnit4::class)


class AuthInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)
    @Test
    fun checkLogin() {
        onView(withId(R.id.editEmail)).perform(
            typeText("sita@gmail.com")
        )

        onView(withId(R.id.editPassword)).perform(
            typeText("password@123")
        )

        closeSoftKeyboard()

        Thread.sleep(1500)

        onView(withId(R.id.btnLogin)).perform(
            click()
        )

        Thread.sleep(1500)
        onView(withId(R.id.displayloginresult)).check(matches(withText("login failed")))
    }

}