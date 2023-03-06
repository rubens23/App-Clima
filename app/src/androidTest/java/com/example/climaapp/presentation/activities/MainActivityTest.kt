package com.example.climaapp.presentation.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.climaapp.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()

        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testNavigationClick(){
        //onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.navigation_clima_global))
        //onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.navigation_clima_global))
    }

    /**
     * eu to tentando testar a navegação só que como
     * eu não estou testando o navigation component
     * acho que esse teste não funciona. Eu to meio
     * que utilizando o itemClickListener na bottom
     * nav. Esse NavigationViewActions deve funcionar só com
     * o navigation component
     */
}