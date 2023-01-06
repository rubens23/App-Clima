package com.example.climaapp.tutorialteste

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers


internal class BiscuitTest{
    @Test
    fun testEquals(){
        val theBiscuit = Biscuit("Ginger")
        val myBiscuit = Biscuit("Chocolate")//mudar o sabor
        assertThat("biscoito de gengibre", myBiscuit, equalTo(theBiscuit))
    }
}