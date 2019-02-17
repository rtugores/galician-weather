package huitca1212.galicianweather.view

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get: Rule
    val activityRule = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {}

    @Test
    fun when_stations_screen_shown_then_11_stations_are_shown() {
        HomeScreenRobot verify { homeScreen { elevenItemsAreShown() } }
    }
}