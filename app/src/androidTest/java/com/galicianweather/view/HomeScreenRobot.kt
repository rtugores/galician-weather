package com.galicianweather.view

import com.schibsted.spain.barista.assertion.BaristaListAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.galicianweather.R

class HomeScreenRobot {

    companion object {
        infix fun verify(func: HomeScreenRobot.() -> Unit) = HomeScreenRobot().apply { func() }
    }

    infix fun homeScreen(func: HomeScreenRobotResult.() -> Unit): HomeScreenRobotResult {
        assertDisplayed(R.id.stationsRecyclerView)
        return HomeScreenRobotResult().apply { func() }
    }
}

class HomeScreenRobotResult {

    fun elevenItemsAreShown() {
        BaristaListAssertions.assertListItemCount(R.id.stationsRecyclerView, 11)
    }
}
