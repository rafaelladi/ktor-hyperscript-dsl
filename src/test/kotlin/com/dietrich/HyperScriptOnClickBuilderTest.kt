package com.dietrich

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class HyperScriptOnClickBuilderTest {

    @Test
    fun testClick() {
        val expected = "on click toggle .red on me"

        val onClickBuilder = HyperScriptOnClickBuilder()
        onClickBuilder.apply {
            toggle(".red")
            on("me")
        }

        assertEquals(expected, onClickBuilder.build())
    }

}