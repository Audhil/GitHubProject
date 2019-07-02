package com.audhil.medium.samplegithubapp

import android.content.Intent
import androidx.test.espresso.Espresso
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
import androidx.test.platform.app.InstrumentationRegistry
import com.audhil.medium.samplegithubapp.util.ConstantsUtil

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
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DummyActivity::class.java)
        intent.putExtra(
            ConstantsUtil.MOCK_URL,
            mockServer.url("/todos/1").toString()
        )
        rule.launchActivity(intent)
        Espresso.onView(ViewMatchers.withId(R.id.dddd))
            .check(ViewAssertions.matches(ViewMatchers.withText("success!!!")))
    }

    @Test
    fun unHappyTest() {
        mockServer.dispatcher = MockServerDispatcher.ErrorDispatcher()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DummyActivity::class.java)
        intent.putExtra(
            ConstantsUtil.MOCK_URL,
            mockServer.url("jack and jill URL").toString()
        )
        rule.launchActivity(intent)
        Espresso.onView(ViewMatchers.withId(R.id.dddd))
            .check(ViewAssertions.matches(ViewMatchers.withText("somethingWentWrong!!!")))
    }
}
