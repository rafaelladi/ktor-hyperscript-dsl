package com.dietrich

import kotlinx.html.HTMLTag
import kotlin.collections.joinToString
import kotlin.collections.mutableListOf
import kotlin.collections.plusAssign
import kotlin.collections.set

enum class Trigger(val value: String) {
    CLICK("click"),
    MOUSE_ENTER("mouseenter"),
    MOUSE_LEAVE("mouseleave")
}

open class HyperScriptBuilder(private val commands: MutableList<String> = mutableListOf()) {
    fun on(trigger: Trigger) {
        commands += "on ${trigger.value}"
    }

    fun on(target: String) {
        commands += "on $target"
    }

    fun toggle(action: String) {
        commands += "toggle $action"
    }

    fun log(message: String) {
        commands += "log '${message.replace("'", "\\'")}'"
    }

    fun build(): String = commands.joinToString(" ")
}

class HyperScriptOnClickBuilder : HyperScriptBuilder(mutableListOf("on click"))

class HyperScript {
    var builder: HyperScriptBuilder? = null

    fun apply(tag: HTMLTag) {
        tag.attributes["_"] = builder?.build() ?: ""
    }
}

fun HyperScript.click(builder: HyperScriptOnClickBuilder = HyperScriptOnClickBuilder(), block: HyperScriptOnClickBuilder.() -> Unit) {
    builder.apply(block)
    this.builder = builder
}

fun HTMLTag.hyperscript(block: HyperScript.() -> Unit) {
    HyperScript().apply(block).apply(this)
}