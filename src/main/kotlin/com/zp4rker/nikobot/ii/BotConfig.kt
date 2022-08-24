package com.zp4rker.nikobot.ii

import kotlinx.serialization.Serializable

lateinit var TOKEN: String
lateinit var VERIFICATION_CHANNEL: String
lateinit var VERIFICATION_ROLE: String

const val APPROVE_EMOJI = "✔️"
const val DENY_EMOJI = "❌"

@Serializable
data class BotConfig (
    val token: String,
    val verificationChannel: String,
    val verificationRole: String
)