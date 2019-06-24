package com.audhil.medium.samplegithubapp

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.audhil.medium.samplegithubapp.dispatcher.MockServerDispatcher
import com.audhil.medium.samplegithubapp.ui.DummyActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

//  working
//@RunWith(AndroidJUnit4::class)
//class BasicActivityTest {
//
//    private lateinit var mockServer: MockWebServer
//
//    @Rule
//    @JvmField
//    val rule = ActivityTestRule(DummyActivity::class.java, false, false)
//
//    @Before
//    fun setUp() {
//        mockServer = MockWebServer()
//    }
//
//    @After
//    fun tearDown() =
//        mockServer.shutdown()
//
//    @Test
//    fun basicTest() {
//        rule.launchActivity(null)
//        mockServer.enqueue(
//            MockResponse().setResponseCode(200).setBody(
//                "{\n" +
//                        "  \"id\" : 1,\n" +
//                        "  \"title\" : \"delectus aut autem\",\n" +
//                        "  \"userId\" : 1,\n" +
//                        "  \"completed\" : false\n" +
//                        "}"
//            )
//        )
//        mockServer.start(8080)
//        val baseUrl = mockServer.url("/todos/1")    //  baseUrl is "http://localhost:8080/todos/1"
//        rule.activity.fetchFromServer(baseUrl.toString())
//        Espresso.onView(ViewMatchers.withId(R.id.dddd))
//            .check(ViewAssertions.matches(ViewMatchers.withText("audhil")))
////            .check(ViewAssertions.matches(ViewMatchers.withText("somethingWentWrong!!!")))
//    }
//}

@RunWith(AndroidJUnit4::class)
class BasicActivityTest {

    private lateinit var mockServer: MockWebServer

    @Rule
    @JvmField
    val rule = ActivityTestRule(DummyActivity::class.java, false, false)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start(8080)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun happyTest() {
        mockServer.dispatcher = MockServerDispatcher.ResponseDispatcher()
        rule.launchActivity(null)
        rule.activity.fetchFromServer(mockServer.url("/todos/1").toString())    //  "http://localhost:8080/todos/1"
        Espresso.onView(ViewMatchers.withId(R.id.dddd))
            .check(ViewAssertions.matches(ViewMatchers.withText("success!!!")))
//        Espresso.onView(ViewMatchers.withId(R.id.dddd))
//            .perform(setTextInTextView("dddd"))
    }

    @Test
    fun unHappyTest() {
        mockServer.dispatcher = MockServerDispatcher.ErrorDispatcher()
        rule.launchActivity(null)
        rule.activity.fetchFromServer(mockServer.url("/todos/1").toString())
        Espresso.onView(ViewMatchers.withId(R.id.dddd))
            .check(ViewAssertions.matches(ViewMatchers.withText("somethingWentWrong!!!")))
    }

    //  setText in TextView
    private fun setTextInTextView(value: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(TextView::class.java))
            }

            override fun perform(uiController: UiController, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }
}