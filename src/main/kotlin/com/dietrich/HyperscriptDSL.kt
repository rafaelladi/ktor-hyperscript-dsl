package com.dietrich

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.h1
import kotlinx.html.stream.appendHTML

interface HyperScriptDSL {
    fun hyperScript(): String
}

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
        commands += "toggle .red"
    }

    fun build(): String = commands.joinToString(" ")
}

fun HTMLTag.hyperScript(builder: HyperScriptBuilder = HyperScriptBuilder(), block: HyperScriptBuilder.() -> Unit) {
    val hyperScript = builder.apply(block).build()
    attributes["_"] = hyperScript
}

suspend inline fun ApplicationCall.respondHtmlFragment(crossinline block: TagConsumer<StringBuilder>.() -> Unit) = respondText {
    buildString { appendHTML().apply(block) }
}

fun Application.routing() {
    routing {
        get {
            call.respondHtmlFragment {
                h1 {
                    hyperScript {
                        on(Trigger.CLICK)
                        toggle(".red")
                        on("me")
                    }
                }
            }
        }
    }
}