package com.dietrich

import kotlinx.html.HTMLTag
import kotlin.collections.joinToString
import kotlin.collections.mutableListOf
import kotlin.collections.plusAssign
import kotlin.collections.set

enum class Trigger(val value: String) {
    CLICK("click")
}

class HyperScriptBuilder {
    private val commands = mutableListOf<String>()

    fun on(trigger: Trigger) {
        commands += "on ${trigger.value}"
    }

    fun on(target: String) {
        commands += "on $target"
    }

    fun toggle(action: String) {
        commands += "toggle $action"
    }

    fun build(): String = commands.joinToString(" ")
}

fun HTMLTag.hyperscript(builder: HyperScriptBuilder = HyperScriptBuilder(), block: HyperScriptBuilder.() -> Unit) {
    val hyperScript = builder.apply(block).build()
    attributes["_"] = hyperScript
}